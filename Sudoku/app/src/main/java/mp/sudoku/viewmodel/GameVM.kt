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
 * All the private vars observes the DB so that they change immediately with it
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
            /* static fun to solve a sudoku. This fun adjust the format of the board changing it from list of list of string
            * to persistence format so that the class SudokuSolver can calculate the answer.
            * Persistence format is "..2....3....1.2..3..."
            * */
            try {
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
                if (solver.solve() == Result.Open) // Solver returns Result.Open if Sudoku as multiple solutions. solver.solveRecursive get one solution out of all
                    solver.solveRecursive()
                return solver.getSudoku()
            } catch (e: Exception) {
                e.printStackTrace()
                return listOf(listOf())
            }

        }

        /*
        * function needed to validate grid in case of multiple sudoku's solutions.
        * For each cell it checks if there is a duplicate on the same row or on the same column.
        * */
        fun validateGrid(board: List<List<String>>): Boolean {
            var valid = true
            try {
                for (row in board) {
                    for (col in row) {
                        if (!checkRow(col, row)) {
                            valid = false
                        }
                        if (!checkColumn(board, col.toInt(), row.indexOf(col))) {
                            valid = false
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                valid = false
            }
            return valid
        }

        private fun checkColumn(board: List<List<String>>, number: Int, index: Int): Boolean {
            var valid = true
            var count = 0

            for (row in board) {
                if (row[index].toInt() == number) {
                    count++
                }
            }
            if (count > 1)
                valid = false
            return valid
        }

        private fun checkRow(s: String, row: List<String>): Boolean {
            var valid = true
            var count = 0

            for (num in row) {
                if (num == s)
                    count++
            }
            if (count > 1)
                valid = false

            return valid
        }
    }


    /*
    * Fun to get a new grid using volley and the API from the website
    * */
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
    fun addGame(board: List<List<String>>, difficulty: String, id: Int) {
        val game = CurrentGame.getInstance().getCurrent()
        try {
            game!!.grid = Adapter.boardListToPersistenceFormat(Adapter.changeStringToInt(board))
            game.firstGrid = game.grid
            game.difficulty = difficulty
            game.lastUpdate = LocalDate.now().toString()
            game.id = id
            if (game.solvedGrid == "empty") {
                game.solvedGrid = Adapter.boardListToPersistenceFormat(solve(board))
                CurrentGame.getInstance().solution =
                    Adapter.changeStringToInt(Adapter.boardPersistenceFormatToList(game.solvedGrid))
            }
            repGame.insertGame(game)
            CurrentGame.getInstance().solution = solve(board)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateGame(
        board: String,
        noteBoard: String,
        timer: String,
        finished: Int = 0,
        cancel: Boolean = true
    ) {
        val game = CurrentGame.getInstance().getCurrent()
        try {
            game!!.grid = board
            game.noteGrid = noteBoard
            game.timer = timer
            game.solvedGrid =
                Adapter.boardListToPersistenceFormat(CurrentGame.getInstance().solution!!)

            game.finished = finished
            if (finished == 1) {
                game.score = calculateScore(game)
            }
            game.lastUpdate = LocalDate.now().toString()
            repGame.updateOne(CurrentGame.getInstance().getCurrent())
            CurrentGame.getInstance().timer.pause()
            if (cancel) {
                CurrentGame.getInstance().current = null
                CurrentGame.getInstance().solution = null
                CurrentGame.getInstance().timer.reset()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun calculateScore(game: Game): Int {
        var score = game.score

        val constBonus = 20

        score += (10000 / StopWatch.formatString(game.timer).toDouble()).toInt()


        when (game.difficulty) {
            "easy" -> score += (constBonus * 1)
            "medium" -> score += (constBonus * 2)
            "hard" -> score += (constBonus * 3)
        }
        return score
    }

    fun deleteOne(game: Game) {
        try {
            repGame.deleteOne(game)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun resumeGame(): List<List<String>> {
        var temp: List<List<String>> = listOf(listOf(""))
        val game = CurrentGame.getInstance().getCurrent()
        try {
            temp =
                Adapter.boardPersistenceFormatToList(game?.grid!!)
            CurrentGame.getInstance().solution = Adapter.changeStringToInt(
                Adapter.boardPersistenceFormatToList(
                    game.solvedGrid
                )
            )
            CurrentGame.getInstance().timer.formattedTime =
                game.timer
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return temp
    }

}