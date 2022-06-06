package mp.sudoku.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import mp.sudoku.model.Game
import mp.sudoku.model.database.DBGame
import mp.sudoku.viewmodel.repository.RepositoryGame

/*
    Class used to keep track of the changes of the entities used in the statistic use case
    Use it like:
        val all by viewmodel.attr.observeStateOf(listof())
 */
class StatisticVM(application:Application) {
    private val repGame: RepositoryGame
    var allGames: LiveData<List<Game>>
    var finishedGames: LiveData<List<Game>>

    init {
        val db = DBGame.getInstance(application) //Singleton instance of the db
        val daoGame = db.gameDAO()

        repGame = RepositoryGame(daoGame)

        allGames = repGame.getAllGames()
        finishedGames = repGame.getFinishedGames()
    }

    //Statics methods to get information about games, when needed
    companion object{
        fun getMaxScore(games: List<Game>):Float{
            var max = 0F
            for(g in games){
                if(max<g.score)
                    max = g.score.toFloat()
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

        fun getBestTime(finishedGames: List<Game>): String {
            var bestTime = 0f
            var tempString = "00:00"

            for(g in finishedGames){
                if(g.timer.toFloat() > bestTime){
                    bestTime = g.timer.toFloat()
                    tempString = g.timer
                }
            }
            return tempString
        }

        fun getAvgTime(finishedGames: List<Game>): String {
            var totTime = 0f
            var avgTime = 0f
            for(g in finishedGames){
                if(g.timer.toFloat() > totTime){
                    totTime = g.timer.toFloat()
                }
            }
            if(finishedGames.isNotEmpty())
                avgTime = totTime/finishedGames.size

            return avgTime.toString()
        }
    }


}