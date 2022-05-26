package mp.sudoku.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import mp.sudoku.model.Game
import mp.sudoku.model.Note
import mp.sudoku.model.SudokuGrid
import mp.sudoku.model.Timer
import mp.sudoku.model.database.DBGame
import mp.sudoku.model.database.repository.RepositoryGame
import mp.sudoku.model.database.repository.RepositoryGrid
import mp.sudoku.model.database.repository.RepositoryNote
import mp.sudoku.model.database.repository.RepositoryTimer


class GameVM(application: Application) {
    private val repGame: RepositoryGame
    private val repGrid: RepositoryGrid
    private val repNote: RepositoryNote
    private val repTimer: RepositoryTimer


    init {
        val db = DBGame.getInstance(application) //Singleton instance of the db
        val daoGame = db.gameDAO()
        val daoGrid = db.gridDAO()
        val daoNote = db.noteDAO()
        val daoTimer = db.timerDAO()

        repGame = RepositoryGame(daoGame)
        repGrid = RepositoryGrid(daoGrid)
        repNote = RepositoryNote(daoNote)
        repTimer = RepositoryTimer(daoTimer)
    }

    //Game func
    fun addGame(game:Game){
        repGame.insertGame(game)
    }

    fun getGames(num: MutableState<Int>){
        repGame.getAllGames(num)
    }

    //Grid func
    fun addGrid(grid:SudokuGrid){
        repGrid.insertGrid(grid)
    }

    fun getGrids(){
        repGrid.getAllGrids()
    }


    //Note func
    fun addNote(note:Note){
        repNote.insertNote(note)
    }

    fun getNotes(){
        repNote.getAllNotes()
    }

    fun addTimer(timer: Timer){
        repTimer.insertTimer(timer)
    }

    fun getTimers(){
        repTimer.getAllTimers()
    }
}