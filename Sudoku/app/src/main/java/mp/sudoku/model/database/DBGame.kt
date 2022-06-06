package mp.sudoku.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mp.sudoku.model.Game
import mp.sudoku.model.database.dao.DAOGame
import mp.sudoku.model.database.util.DBName

@Database(entities = [Game::class],version = 1,exportSchema = false)
abstract class DBGame: RoomDatabase() {
    companion object{
        private var INSTANCE: DBGame? = null
        fun getInstance(context : Context): DBGame {
            val tempInstance = INSTANCE
            if(tempInstance != null)
                return  tempInstance
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, DBGame::class.java,DBName.DBNAME).createFromAsset(DBName.DBNAME).build()
                INSTANCE = instance
                return instance
            }
        }

    }
    abstract fun gameDAO(): DAOGame
}