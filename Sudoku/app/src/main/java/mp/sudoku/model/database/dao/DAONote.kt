package mp.sudoku.model.database.dao

import androidx.room.*
import mp.sudoku.model.Note



@Dao
interface DAONote {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOne(note: Note)
    @Query("SELECT * FROM Note")
    fun getAllNotes():List<Note>
}
