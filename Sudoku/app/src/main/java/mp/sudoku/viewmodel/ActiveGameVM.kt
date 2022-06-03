package mp.sudoku.viewmodel

import android.app.Application
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import mp.sudoku.ui.theme.BackgroundWhite

class ActiveGameVM() {
    internal val gridState: HashMap<Int, SudokuCell> = HashMap()
    internal var subGridState: ((HashMap<Int, SudokuCell>) -> Unit)? = null
    internal var notesMode: Boolean = false

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
                gridState[(i * 10 + j)] = SudokuCell(
                    x = j,
                    y = i,
                    value = list[i][j],
                    isSelected = false,
                    nonet = findNonet(j, i),
                    isOnFocus = false,
                    isReadOnly = (list[i][j] != 0),
                    note = 0
                )
            }
        }
    }

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
            if (it.isSelected) {
                if (notesMode) {
                    it.value = 0
                    it.note = value
                } else {
                    it.note = 0
                    it.value = value
                }
            }
         }
        subGridState?.invoke(gridState)
    }

    fun selectCell(
        x: Int,
        y: Int
    ) {
        gridState.values.forEach {
            it.isSelected = false
            it.isOnFocus = false
            if (it.x == x && it.y == y)
                it.isSelected = true
            else if (gridState[x*10 + y]?.value == 0) {
                if (it.x == x || it.y == y)
                    it.isOnFocus = true
            }
        }
        subGridState?.invoke(gridState)
    }

    fun cancelCell() {
        gridState.values.forEach {
            if (it.isSelected) {
                it.note = 0
                it.value = 0
            }
        }
        subGridState?.invoke(gridState)
    }
}


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