package mp.sudoku.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mp.sudoku.model.Game
import mp.sudoku.model.Note
import mp.sudoku.model.SudokuGrid
import mp.sudoku.model.Timer
import mp.sudoku.model.database.DBGame
import mp.sudoku.model.database.repository.RepositoryGame
import mp.sudoku.model.database.repository.RepositoryGrid
import mp.sudoku.model.database.repository.RepositoryNote
import mp.sudoku.model.database.repository.RepositoryTimer


class GameVM(application: Application) :AndroidViewModel(application){

    /* To insert Volley
    private val data: MutableLiveData<List<SudokuGrid>> by lazy {
        MutableLiveData<List<SudokuGrid>>().also {
            loadData(application)
        }
    }

    private val date: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }

    private val current: MutableLiveData<SudokuGrid> by lazy {
        MutableLiveData<SudokuGrid>()
    }


    fun setDate(d: String) {
        date.value = d
        if(data.value != null) {
            for (c in data.value!!) {
                if (c.grid.substring(0..9) == date.value) {
                    current.value = c
                }
            }
        }
    }

    fun getData(): LiveData<List<SudokuGrid>> {
        return data
    }

    fun getLast(): MutableLiveData<SudokuGrid> {
        return current
    }
    private fun loadData(context: Context,difficulty:String) {
        val url = "https://sugoku.herokuapp.com/board?difficulty=$difficulty"
        val latestQueue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url, { response ->
                Log.w("LATEST", response)

                val sType = object : TypeToken<List<SudokuGrid>>() {}.type
                val gson = Gson()
                val mData = gson.fromJson<List<SudokuGrid>>(response, sType)
                data.value = mData
                current.value = mData[mData.lastIndex]
            },
            {
                Log.e("LATEST", it.message!!)
            })
        latestQueue.add(stringRequest)
    }
*/

}