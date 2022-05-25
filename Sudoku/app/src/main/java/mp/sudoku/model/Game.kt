package mp.sudoku.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
class Game {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @NotNull
    @ColumnInfo(defaultValue = "0")
    var hintCounter: Int = 0

    @NotNull
    @ColumnInfo(defaultValue = "0")
    var finished: Int = 0

    @NotNull
    @ColumnInfo(defaultValue = "0")
    var score: Int = 0

    @NotNull
    @ColumnInfo(defaultValue = "easy")
    var difficulty: String = "easy"

    @ColumnInfo(defaultValue = "null")
    var finishDate: String? = null
}
