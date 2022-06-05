package mp.sudoku.viewmodel

class ActiveGameVM {

    internal val gridState: HashMap<Int, SudokuCell> = HashMap()
    internal var subGridState: ((HashMap<Int, SudokuCell>) -> Unit)? =
        null     // this is useful to commit changes to the view

    internal var isCompleted =
        false                                            // true if grid is full
    internal var subCompletedState: ((Boolean) -> Unit)? =
        null                 // this is useful to commit changes to the view

    internal var notesMode =
        false                                              // true if user is inserting notes


    fun initGrid(list: List<List<Int>>) {


        for (i in list.indices) {               // i = number of row
            for (j in list[i].indices) {        // j = number of element in the row

                /*
                *   The key can be seen as the matrix indexes (row, col).
                *
                *   For example, the second element in the first row will have key 12,
                *   the fourth element of the seventh row will have key 74, and so on.
                *
                */
                gridState[(j * 10 + i)] = SudokuCell(
                    x = j,
                    y = i,
                    value = list[i][j],
                    isSelected = false,
                    nonet = findNonet(j, i),            // a nonet is a 3x3 sub-block
                    isInEvidence = false,                  // the cell is on focus if it's placed on the same row/column/nonet of the selected cell
                    isReadOnly = (list[i][j] != 0),     // the read only cells are those present in the initial grid
                    note = 0
                )
            }
        }
    }

    /* this function, given x and y coordinates of the cell, computes its nonet of belonging */
    private fun findNonet(x: Int, y: Int): Int {
        when {
            y < 3 -> {
                return when {
                    x < 3 -> 1
                    x < 6 -> 2
                    else -> 3
                }
            }
            y < 6 -> {
                return when {
                    x < 3 -> 4
                    x < 6 -> 5
                    else -> 6
                }
            }
            else -> {
                return when {
                    x < 3 -> 7
                    x < 6 -> 8
                    else -> 9
                }
            }
        }
    }

    fun updateGrid(
        value: Int
    ) {
        gridState.values.forEach {
            if (it.isSelected) {        // the following operations are going to be applied only to the selected cell
                if (notesMode) {        // if notes mode is on and the user inserts a note on the cell, the previously present value (if any) is cancelled
                    it.value = 0
                    it.note = value
                } else {                // if notes mode is off and the user inserts a value on the cell, the previously present note (if any) is cancelled
                    it.note = 0
                    it.value = value
                }
            }
        }

        if (checkIfFull()) subCompletedState?.invoke(true)     // if the grid is full, inform the view to show the "Check" button

        subGridState?.invoke(gridState)                        // commit grid changes to the view
    }

    private fun checkIfFull(): Boolean {
        var bool = true

        gridState.values.forEach {
            if (it.value == 0) bool = false
        }

        return bool
    }

    fun selectCell(
        x: Int,
        y: Int
    ) {
        gridState.values.forEach {
            it.isSelected = false
            it.isInEvidence = false
            if (it.x == x && it.y == y)
                it.isSelected = true        // mark the (x, y) cell as selected
            if (it.x == x || it.y == y || it.nonet == gridState[x*10+y]?.nonet)
                it.isInEvidence = true         // put every cell on the same row/column/nonet in evidence

        }
        subGridState?.invoke(gridState)     // commit changes to the view
    }

    fun cancelCell() {
        gridState.values.forEach {
            if (it.isSelected) {
                it.note = 0
                it.value = 0
            }
        }
        subGridState?.invoke(gridState)     // commit changes to the view
    }
}

class SudokuCell(
    val x: Int,
    val y: Int,
    var value: Int,
    var isSelected: Boolean,
    val nonet: Int,
    var isReadOnly: Boolean,
    var isInEvidence: Boolean,
    var note: Int
)