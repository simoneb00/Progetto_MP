package mp.sudoku.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(    foreignKeys = [ForeignKey(
    entity = Game::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("game"),
    onDelete = ForeignKey.CASCADE
)])
class Note {
    @PrimaryKey(autoGenerate = true)
    var game:Int = 0
    var description:String = ""
    var column:Int = 0
    var row:Int = 0
}