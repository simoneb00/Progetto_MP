package mp.sudoku.viewmodel

import android.app.Application
import mp.sudoku.model.Game
import mp.sudoku.model.database.DBGame
import mp.sudoku.model.database.RepositoryGame


class GameVM(application: Application) {
    private val rep: RepositoryGame

    init {
        val db = DBGame.getInstance(application)
        val dao = db.gameDAO()
        rep = RepositoryGame(dao)
    }

    fun addGame(game:Game){
        rep.insertGame(game)
    }

    fun getGames(){
        rep.getAllGames()
    }
}