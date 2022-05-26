package mp.sudoku.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(    foreignKeys = [ForeignKey(
    entity = Game::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("game"),
    onDelete = ForeignKey.CASCADE
)])
class Note {
    @PrimaryKey(autoGenerate = true)
    var game:Int = 0
    @NotNull
    var description:String = ""
    @NotNull
    var column:Int = 0
    @NotNull
    var row:Int = 0
}