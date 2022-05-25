package mp.sudoku.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Game {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
