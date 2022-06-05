package mp.sudoku.viewmodel.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mp.sudoku.model.Timer
import mp.sudoku.model.database.dao.DAOTimer

class RepositoryTimer(private val daoTimer: DAOTimer){
    var listTimer: LiveData<List<Timer>> = daoTimer.getAllTimers()


    fun insertTimer(timer: Timer){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                daoTimer.insertOne(timer)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun getAllTimers(){/*
        CoroutineScope(Dispatchers.IO).launch {
            val timers:List<Timer>
            try {
                timers = listTimer
                for(i in timers){
                    System.out.println("Game:" + i.value)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }*/
    }


    fun getBestTimer(time: MutableList<Timer>){
        CoroutineScope(Dispatchers.IO).launch {
            /*val timers:List<Timer>
            try {
                timers = daoTimer.getAllTimers()
                time.addAll(timers)
                for(t in timers){
                    System.out.println(t.value)
                }
                System.out.println("ciao")
            }catch (e:Exception){
                e.printStackTrace()
            }*/
        }
    }

}