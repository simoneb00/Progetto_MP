package mp.sudoku.model.database.dao

import androidx.room.*
import mp.sudoku.model.Game
import mp.sudoku.model.SudokuGrid


@Dao
interface DAOGrid {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOne(grid: SudokuGrid)
    @Update
    fun updateOne(grid: SudokuGrid)
    @Query("SELECT * FROM SudokuGrid")
    fun getAllGrids():List<SudokuGrid>
}
