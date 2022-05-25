package mp.sudoku.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mp.sudoku.model.Game
import mp.sudoku.model.Note
import mp.sudoku.model.SudokuGrid
import mp.sudoku.model.Timer
import mp.sudoku.model.database.dao.DAOGame
import mp.sudoku.model.database.dao.DAOGrid
import mp.sudoku.model.database.dao.DAONote
import mp.sudoku.model.database.dao.DAOTimer
import mp.sudoku.model.database.util.DBName

@Database(entities = [Game::class, SudokuGrid::class, Note::class, Timer::class],version = 1)
abstract class DBGame: RoomDatabase() {
    companion object{
        private var db: DBGame? = null
        fun getInstance(context : Context): DBGame {
            val dbName = DBName()
            if(db == null)
                db = Room.databaseBuilder(context.applicationContext, DBGame::class.java,dbName.DBNAME).createFromAsset(dbName.DBNAME).build()
            return db as DBGame
        }

    }
    abstract fun gameDAO(): DAOGame

    abstract fun gridDAO(): DAOGrid

    abstract fun noteDAO(): DAONote

    abstract fun timerDAO(): DAOTimer

}