package mp.sudoku.model.database.repository

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mp.sudoku.model.Game
import mp.sudoku.model.database.dao.DAOGame

class RepositoryGame (private val daoGame: DAOGame){

    fun insertGame(game:Game){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                daoGame.insertOne(game)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun getAllGames(num: MutableState<Int>){
        CoroutineScope(Dispatchers.IO).launch {
            val games:List<Game>
            try {
                games = daoGame.getAllGames()
                num.value = games.size
                for(i in games){
                    System.out.println("Game:" + i.id)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun getWonGames(num: MutableState<Int>){
            CoroutineScope(Dispatchers.IO).launch {
                val games:List<Game>
                try {
                    games = daoGame.getWonGames()
                    num.value = games.size
                    for(i in games){
                        System.out.println("Game:" + i.id)
                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
    }

}