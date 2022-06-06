package mp.sudoku.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mp.sudoku.model.Game
import mp.sudoku.model.database.DBGame
import mp.sudoku.model.volley.VolleyGrid
import mp.sudoku.model.volley.VolleySingleton
import mp.sudoku.viewmodel.repository.RepositoryGame
import mp.sudoku.viewmodel.repository.RepositoryTimer


/*
 *  ViewModel utile per gestire il caricamento via volley di un nuovo schema di sudoku. Utilizzare nel seguente modo
 *              val myVM: GameVM by viewModels()
                val last = remember {
                    mutableStateOf(
                        VolleyGrid()
                    )
                }
                myVM.getData()

                myVM.getData().observe(this) { d -> last.value = d }

                Text(text = "${last.value.board[0][0]}")
 */


class GameVM(application: Application) : AndroidViewModel(application) {
    var grid: VolleyGrid = VolleyGrid()
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
/*
    fun getData(difficulty: String) {
        loadData(app, difficulty)
    }*/


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
    fun addGame(game: Game){
        repGame.insertGame(game)
    }

}