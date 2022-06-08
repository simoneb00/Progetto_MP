package mp.sudoku.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import mp.sudoku.model.CurrentGame
import mp.sudoku.model.Game
import mp.sudoku.model.database.DBGame
import mp.sudoku.model.volley.VolleySingleton
import mp.sudoku.viewmodel.repository.RepositoryGame
import java.time.LocalDate


/*
 *  ViewModel utile per gestire il caricamento via volley di un nuovo schema di sudoku.
 */


class GameVM(application: Application) : AndroidViewModel(application) {
    private val repGame: RepositoryGame

    private val app = application

    init {
        val db = DBGame.getInstance(application) //Singleton instance of the db
        val daoGame = db.gameDAO()

        repGame = RepositoryGame(daoGame)
    }

    companion object {
        fun solve(board: List<List<String>>): List<List<Int>> {
            var adaptedGrid = ""
            for (i in board) {
                for (j in i) {
                    if (j.toInt() == 0)
                        adaptedGrid.plus(".").also { adaptedGrid = it }
                    else
                        adaptedGrid.plus(j).also { adaptedGrid = it }
                }
                adaptedGrid.plus("\n").also { adaptedGrid = it }
            }

            val solver = SudokuSolver(adaptedGrid.trimIndent())
            try {
                if(solver.solve() == Result.Open)
                    solver.solveRecursive()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return solver.getSudoku()
        }
    }


    fun loadData(
        difficulty: String,
        onSuccess: (String) -> Unit
    ) {
        val url = "https://sugoku.herokuapp.com/board?difficulty=$difficulty"
        val volley = VolleySingleton.getInstance()
        val stringRequest = StringRequest(
            Request.Method.GET,
            url, {
                onSuccess(it)
            },
            {
               Log.e("LATEST", it.message!!)
            })
        volley.getRequestQueue(app)?.add(stringRequest)
    }

    //Game func
    fun addGame(board: List<List<String>>, difficulty: String, id: Int){
        val game = CurrentGame.getInstance().getCurrent()
        try{
            game!!.grid = Adapter.boardListToPersistenceFormat(Adapter.changeStringToInt(board))
            game.difficulty = difficulty
            game.lastUpdate = LocalDate.now().toString()
            game.id = id
            repGame.insertGame(game)
            CurrentGame.getInstance().solution = solve(board)
        }catch(e:Exception){
            e.printStackTrace()
        }
    }

    fun updateGame(board: String, noteBoard: String, timer: String,finished:Int = 0) {
        val game = CurrentGame.getInstance().getCurrent()
        try{
            game!!.grid = board
            game.noteGrid = noteBoard
            game.timer = timer
            game.finished = finished
            if(finished == 1)
            {
                game.score = calculateScore(game)
            }
            game.lastUpdate = LocalDate.now().toString()
            repGame.updateOne(CurrentGame.getInstance().getCurrent())
            CurrentGame.getInstance().deleteCurrent()
            CurrentGame.getInstance().solution = null
            CurrentGame.getInstance().timer = StopWatch()
        }catch(e:Exception){
            e.printStackTrace()
        }
    }

    private fun calculateScore(game: Game): Int {
        var score = game.score
        //TODO
        return score
    }

    fun deleteOne(game: Game) {
        try{
            repGame.deleteOne(game)
        }catch(e:Exception){
            e.printStackTrace()
        }
    }

    fun resumeGame(): List<List<String>> {
        var temp:List<List<String>> = listOf(listOf(""))
        try {
            temp = Adapter.boardPersistenceFormatToList(CurrentGame.getInstance().getCurrent()?.grid!!)
            CurrentGame.getInstance().solution = solve(temp)
            CurrentGame.getInstance().timer.formattedTime = CurrentGame.getInstance().current!!.timer
        }catch (e:Exception){
            e.printStackTrace()
        }

        return temp
    }

}