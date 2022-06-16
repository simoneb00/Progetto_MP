package mp.sudoku.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import mp.sudoku.R
import mp.sudoku.model.Game
import mp.sudoku.model.database.DBGame
import mp.sudoku.viewmodel.repository.RepositoryGame
import java.time.Duration
import kotlin.time.toKotlinDuration

/*
    Class used to keep track of the changes of the entities used in the statistic use case
    Use it like:
        val all by viewmodel.attr.observeStateOf(listof())
 */
class StatisticVM(application: Application) {
    private val repGame: RepositoryGame
    var allGames: LiveData<List<Game>>
    var finishedGames: LiveData<List<Game>>
    var startedGames: LiveData<List<Game>>

    init {
        val db = DBGame.getInstance(application) //Singleton instance of the db
        val daoGame = db.gameDAO()

        repGame = RepositoryGame(daoGame)

        allGames = repGame.getAllGames()
        finishedGames = repGame.getFinishedGames()
        startedGames = repGame.getStartedGames()
    }

    //Statics methods to get information about games, when needed
    companion object {
        fun getMaxScore(games: List<Game>): Float {
            var max = 0F
            for (g in games) {
                if (max < g.score)
                    max = g.score.toFloat()
            }
            return max
        }

        fun getAvgScore(games: List<Game>): Float {
            return if (games.isEmpty()) {
                0f
            } else {
                var sum = 0F
                for (g in games) {
                    sum += g.score
                }
                (sum / games.size)
            }
        }

        fun getBestTime(finishedGames: List<Game>): String {
            if (finishedGames.isNotEmpty()) {
                var bestTime = finishedGames[0].timer

                for (g in finishedGames) {
                    if (timeToSeconds(g.timer) < timeToSeconds(bestTime))
                        bestTime = g.timer
                }

                val duration = Duration.ofSeconds(timeToSeconds(bestTime).toLong())

                return duration.toKotlinDuration().toString()
            }

            return ""
        }

        private fun timeToSeconds(time: String): Int {
            val list = time.split(":")
            val minutes: Int = list[0].toInt()
            val seconds: Int = list[1].toInt()

            return (minutes * 60 + seconds)
        }

        @RequiresApi(Build.VERSION_CODES.S)
        fun getAvgTime(finishedGames: List<Game>): String {
            var list: List<Duration> = emptyList()

            if (finishedGames.isNotEmpty()) {
                for (g in finishedGames) {
                    (list + timeToLocalTime(g.timer)).also { list = it }
                }

                var totalDuration: Duration = Duration.ofSeconds(0)
                for (duration in list) {
                    totalDuration += duration
                }

                val kotlinDuration = totalDuration.toKotlinDuration()

                val averageDuration = Duration.ofSeconds(kotlinDuration.inWholeSeconds / finishedGames.size)

                return averageDuration.toKotlinDuration().toString()
            }

            return ""
        }

        private fun timeToLocalTime(timer: String): Duration {

            val list = timer.split(":")

            return Duration.ofSeconds((list[0].toInt() * 60 + list[1].toInt()).toLong())
        }
    }


}