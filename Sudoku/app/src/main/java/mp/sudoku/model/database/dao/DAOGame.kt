package mp.sudoku.model.database.dao

import androidx.room.*
import mp.sudoku.model.Game


@Dao
interface DAOGame {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOne(game: Game)
    @Query("SELECT * FROM Game")
    fun getAllGames():List<Game>
}
