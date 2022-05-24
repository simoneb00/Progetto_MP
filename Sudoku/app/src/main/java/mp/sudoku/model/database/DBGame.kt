package mp.sudoku.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mp.sudoku.model.Game
import mp.sudoku.model.database.util.DBName

@Database(entities = [Game::class],version = 1)
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
    abstract fun gameDAO():DAOGame
}