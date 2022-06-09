package mp.sudoku.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import mp.sudoku.model.Game


@Dao
interface DAOGame {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOne(game: Game)
    @Update
    fun updateOne(game:Game)
    @Delete
    fun deleteOne(game: Game)
    @Query("SELECT * FROM Game")
    fun getAll():LiveData<List<Game>>
    @Query("SELECT * FROM Game WHERE finished = 1")
    fun getFinishedGames():LiveData<List<Game>>
    @Query("SELECT * FROM Game WHERE finished = 0")
    fun getStartedGames():LiveData<List<Game>>
}
