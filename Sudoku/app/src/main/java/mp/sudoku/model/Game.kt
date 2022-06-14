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
    @ColumnInfo(defaultValue = "100")
    var score: Int = 100

    @NotNull
    @ColumnInfo(defaultValue = "easy")
    var difficulty: String = "easy"

    @NotNull
    @ColumnInfo(defaultValue = "01-01-2022")
    var lastUpdate: String = "01-01-2022"

    @NotNull
    @ColumnInfo(defaultValue = "empty")
    var grid: String = "empty"

    @NotNull
    @ColumnInfo(defaultValue = "00:00")
    var timer: String = "00:00"

    @NotNull
    @ColumnInfo(defaultValue = "empty")
    var noteGrid: String = "empty"

    @NotNull
    @ColumnInfo(defaultValue = "empty")
    var solvedGrid: String = "empty"

    @NotNull
    @ColumnInfo(defaultValue = "empty")
    var firstGrid: String = "empty"
}
