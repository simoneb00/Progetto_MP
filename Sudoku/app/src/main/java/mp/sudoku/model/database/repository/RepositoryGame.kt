package mp.sudoku.model.database.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mp.sudoku.model.Game
import mp.sudoku.model.database.dao.DAOGame

class RepositoryGame (private val daoGame: DAOGame){
    private var allGames: LiveData<List<Game>> = daoGame.getAll()
    private var allFinishedGames: LiveData<List<Game>> = daoGame.getWonGames()

    fun insertGame(game:Game){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                daoGame.insertOne(game)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun getAllGames(): LiveData<List<Game>> {
        return allGames
    }

    fun getFinishedGames(): LiveData<List<Game>> {
        return allFinishedGames
    }

}