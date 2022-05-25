package mp.sudoku.model.database.dao

import androidx.room.*
import mp.sudoku.model.SudokuGrid


@Dao
interface DAOGrid {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOne(grid: SudokuGrid)
    @Query("SELECT * FROM SudokuGrid")
    fun getAllGrids():List<SudokuGrid>
}
