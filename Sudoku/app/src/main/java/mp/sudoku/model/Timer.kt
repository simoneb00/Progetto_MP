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
class Timer {
    @PrimaryKey(autoGenerate = true)
    var game:Int = 0
    var value:String = "00:00:00"
}