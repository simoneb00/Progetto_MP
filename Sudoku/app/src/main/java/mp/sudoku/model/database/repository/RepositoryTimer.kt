package mp.sudoku.model.database.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mp.sudoku.model.Timer
import mp.sudoku.model.database.dao.DAOTimer

class RepositoryTimer(private val daoTimer: DAOTimer){

    fun insertTimer(timer: Timer){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                daoTimer.insertOne(timer)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun getAllTimers(){
        CoroutineScope(Dispatchers.IO).launch {
            val timers:List<Timer>
            try {
                timers = daoTimer.getAllTimers()
                for(i in timers){
                    System.out.println("Game:" + i.value)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}