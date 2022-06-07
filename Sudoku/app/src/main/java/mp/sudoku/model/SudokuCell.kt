package mp.sudoku.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SudokuCell(
    val x: Int,
    val y: Int,
    var value: Int,
    var isSelected: Boolean,
    val nonet: Int,
    var isReadOnly: Boolean,
    var isInEvidence: Boolean,
    var note: Int
) : Parcelable