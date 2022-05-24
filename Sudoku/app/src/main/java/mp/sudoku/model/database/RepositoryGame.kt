package mp.sudoku.model.database

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mp.sudoku.model.Game

class RepositoryGame (private val daoGame:DAOGame){

    fun insertGame(game:Game){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                daoGame.insertOne(game)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun getAllGames(){
        CoroutineScope(Dispatchers.IO).launch {
            val games:List<Game>
            try {
                games = daoGame.getAllGames()
                for(i in games){
                    System.out.println("Game:" + i.id)
                }

            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}