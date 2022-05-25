package mp.sudoku.viewmodel

import android.app.Application
import mp.sudoku.model.Game
import mp.sudoku.model.SudokuGrid
import mp.sudoku.model.database.DBGame
import mp.sudoku.model.database.repository.RepositoryGame
import mp.sudoku.model.database.repository.RepositoryGrid


class GameVM(application: Application) {
    private val rep: RepositoryGame
    private val rep2: RepositoryGrid

    init {
        val db = DBGame.getInstance(application)
        val dao = db.gameDAO()
        val dao2 = db.gridDAO()
        rep = RepositoryGame(dao)
        rep2 = RepositoryGrid(dao2)
    }

    fun addGame(game:Game){
        rep.insertGame(game)
    }

    fun getGames(){
        rep.getAllGames()
    }

    fun addGrid(grid:SudokuGrid){
        rep2.insertGrid(grid)
    }

    fun getGrids(){
        rep2.getAllGrids()
    }
}