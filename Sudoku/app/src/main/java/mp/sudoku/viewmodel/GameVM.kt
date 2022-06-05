package mp.sudoku.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mp.sudoku.model.volley.VolleyGrid
import mp.sudoku.model.volley.VolleySingleton


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


class GameVM(application: Application) :AndroidViewModel(application){
    val grid: MutableLiveData<VolleyGrid> by lazy {
        MutableLiveData<VolleyGrid>().also {
            loadData(application,"easy")
        }
    }

    companion object{
        fun solve(s:List<List<String>>){
            var adaptedGrid = ""
            for(i in s){
                for(j in i){
                    if (j.toInt() == 0)
                        adaptedGrid.plus(".").also { adaptedGrid = it }
                    else
                        adaptedGrid.plus(j).also { adaptedGrid = it }
                }
            }
            try{
                val solver = SudokuSolver(adaptedGrid)
                println(solver.solveRecursive())
                println(solver.sudoku)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun getData(): MutableLiveData<VolleyGrid> {
        return grid
    }

    private fun loadData(context: Context, difficulty: String) {
        val url = "https://sugoku.herokuapp.com/board?difficulty=$difficulty"
        val volley = VolleySingleton.getInstance()
        val stringRequest = StringRequest(
            Request.Method.GET,
            url, { response ->
                Log.w("LATEST", response)
                val sType = object : TypeToken<VolleyGrid>() {}.type
                val gson = Gson()
                val mData = gson.fromJson<VolleyGrid>(response, sType)
                grid.value = mData
            },
            {
                Log.e("LATEST", it.message!!)
            })
        volley.getRequestQueue(context)?.add(stringRequest)
    }


}