package mp.sudoku.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity( foreignKeys = [ForeignKey(
        entity = Game::class,
        onDelete =ForeignKey.CASCADE,
        onUpdate = ForeignKey.NO_ACTION,
        parentColumns = ["id"],
        childColumns = ["game"])
    ]
)
class SudokuGrid {
    @PrimaryKey(autoGenerate = true)
    var game:Int = 0
    @NotNull
    @ColumnInfo(defaultValue = "empty")
    var grid:String = ""
}