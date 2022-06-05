package mp.sudoku.model

class SudokuCell(
    val x: Int,
    val y: Int,
    var value: Int,
    var isSelected: Boolean,
    val nonet: Int,
    var isReadOnly: Boolean,
    var isOnFocus: Boolean,
    var note: Int
)