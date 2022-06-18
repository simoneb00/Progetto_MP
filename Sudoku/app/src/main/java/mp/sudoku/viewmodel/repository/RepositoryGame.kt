package mp.sudoku.viewmodel.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mp.sudoku.model.Game
import mp.sudoku.model.database.dao.DAOGame
/*
* Class used as repository to ask persistance, in particular Room DAOs
* All the private vars observes the DB so that they change immediately with it
* */
class RepositoryGame (private val daoGame: DAOGame){
    private var allGames: LiveData<List<Game>> = daoGame.getAll()
    private var allFinishedGames: LiveData<List<Game>> = daoGame.getFinishedGames()
    private var allStartedGames: LiveData<List<Game>> = daoGame.getStartedGames()

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



    fun getStartedGames(): LiveData<List<Game>> {
        return allStartedGames
    }

    fun updateOne(game: Game?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                daoGame.updateOne(game!!)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun deleteOne(game: Game?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                daoGame.deleteOne(game!!)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

}