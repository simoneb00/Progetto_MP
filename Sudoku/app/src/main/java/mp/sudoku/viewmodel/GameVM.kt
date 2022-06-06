package mp.sudoku.viewmodel

import android.app.Application
import android.util.Log
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
        fun solve(s: List<List<String>>) {
            var adaptedGrid = ""
            for (i in s) {
                for (j in i) {
                    if (j.toInt() == 0)
                        adaptedGrid.plus(".").also { adaptedGrid = it }
                    else
                        adaptedGrid.plus(j).also { adaptedGrid = it }
                }
            }
            try {
                val solver = SudokuSolver(adaptedGrid)
                println(solver.solveRecursive())
                println(solver.getSudoku())
            } catch (e: Exception) {
                e.printStackTrace()
            }
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
    fun addGame(board: List<List<String>>, difficulty: String){
        val game = Game()
        game.grid = board.toString()
        game.difficulty = difficulty
        game.lastUpdate = LocalDate.now().toString()
        game.score = 100

        repGame.insertGame(game)
    }

    fun updateGame(board: Any?, noteBoard: Any?, timer: Any?, hintCounter: Any?, score: Any?) {
        repGame.updateOne(CurrentGame.getInstance().getCurrent())
    }

}