package mp.sudoku.viewmodel

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

/*
* Classe che serve per gestire il timer. Va utilizzata nel model come:
*
*   val stopwatch = remember {
*        StopWatch()
*    }
*
* la variabile formattedTime restituisce il tempo in stringa del cronometro.
* Utilizzare sempre la classe Stopwatch come parametro per il passaggio da uno scope ad un altro
* */
@Parcelize
class StopWatch : Parcelable {
    @IgnoredOnParcel
    var formattedTime by mutableStateOf("00:00")

    @IgnoredOnParcel
    private var coroutineScope = CoroutineScope(Dispatchers.Main) //Do not change scope or it will broke
    @IgnoredOnParcel
    private var isActive = false

    @IgnoredOnParcel
    private var timeMillis = 0L
    @IgnoredOnParcel
    private var lastTimestamp = 0L

    fun start() {
        if(isActive) return


        timeMillis = formatString(formattedTime)


        coroutineScope.launch {
            lastTimestamp = System.currentTimeMillis()
            this@StopWatch.isActive = true
            while(this@StopWatch.isActive) {
                delay(10L)
                timeMillis += System.currentTimeMillis() - lastTimestamp
                lastTimestamp = System.currentTimeMillis()
                formattedTime = formatTime(timeMillis)
                if(timeMillis>1000000000000)
                {
                    this@StopWatch.isActive = false
                    //TODO verifica se aumenta ad un'ora
                }
            }
        }
    }

    fun pause() {
        isActive = false
    }

    fun reset() {
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)
        timeMillis = 0L
        lastTimestamp = 0L
        formattedTime = "00:00"
        isActive = false
    }

    private fun formatTime(timeMillis: Long): String {
        val localDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timeMillis),
            ZoneId.systemDefault()
        )
        val formatter = DateTimeFormatter.ofPattern(
            "mm:ss",
            Locale.getDefault()
        )
        return localDateTime.format(formatter)
    }

    private fun formatString(time: String): Long {
        var millis = 0L
        val temp = time.split(":")
        millis+=temp[0].toInt()*60*1000L
        millis+=temp[1].toInt()*1000L
        return millis
    }

}