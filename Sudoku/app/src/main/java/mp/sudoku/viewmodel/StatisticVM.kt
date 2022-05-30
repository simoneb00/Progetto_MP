package mp.sudoku.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import mp.sudoku.model.Game
import mp.sudoku.model.Note
import mp.sudoku.model.SudokuGrid
import mp.sudoku.model.Timer
import mp.sudoku.model.database.DBGame
import mp.sudoku.model.database.repository.RepositoryGame
import mp.sudoku.model.database.repository.RepositoryGrid
import mp.sudoku.model.database.repository.RepositoryNote
import mp.sudoku.model.database.repository.RepositoryTimer

/*
    Class used to keep track of the changes of the entities used in the statistic use case
    Use it like:
        val all by viewmodel.attr.observeStateOf(listof())
 */
class StatisticVM(application:Application) {
    private val repGame: RepositoryGame
    private val repTimer: RepositoryTimer
    var allTimer: LiveData<List<Timer>>
    var allGames: LiveData<List<Game>>
    var finishedGames: LiveData<List<Game>>

    init {
        val db = DBGame.getInstance(application) //Singleton instance of the db
        val daoGame = db.gameDAO()
        val daoTimer = db.timerDAO()

        repGame = RepositoryGame(daoGame)
        repTimer = RepositoryTimer(daoTimer)

        allTimer = repTimer.listTimer
        allGames = repGame.getAllGames()
        finishedGames = repGame.getFinishedGames()
    }

    //Statics methods to get information about games, when needed
    companion object{
        fun getMaxScore(games: List<Game>):Int{
            var max = 0
            for(g in games){
                if(max<g.score)
                    max = g.score
            }
            return max
        }

        fun getAvgScore(games: List<Game>): Float {
            var sum = 0F
            for(g in games){
                sum+=g.score
            }
            return (sum/games.size)
        }
    }


    //Game func
    fun addGame(game: Game){
        repGame.insertGame(game)
    }


    @JvmName("getFinishedGames1")
    fun getFinishedGames(): LiveData<List<Game>> {
        return finishedGames
    }

    //Timer fun
    fun getTimers(): LiveData<List<Timer>> {
        return allTimer
    }

}