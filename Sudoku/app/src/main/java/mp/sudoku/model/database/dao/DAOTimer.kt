package mp.sudoku.model.database.dao

import androidx.room.*
import mp.sudoku.model.Timer


@Dao
interface DAOTimer {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOne(timer: Timer)
    @Query("SELECT * FROM Timer")
    fun getAllTimers():List<Timer>
}
