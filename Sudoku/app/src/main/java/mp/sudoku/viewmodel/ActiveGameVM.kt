package mp.sudoku.viewmodel

import android.app.Application
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import mp.sudoku.ui.theme.BackgroundWhite

class ActiveGameVM() {
    internal val gridState: HashMap<Int, SudokuCell> = HashMap()
    internal var subGridState: ((HashMap<Int, SudokuCell>) -> Unit)? = null

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
                    nonet = findNonet(j, i)
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
        key: Int,
        value: Int
    ) {
        gridState[key]?.let {
            it.value = value
        }
    }

    fun selectCell(
        x: Int,
        y: Int
    ) {

        println("x = $x")
        println("y = $y")
        println("nonet = ${findNonet(x, y)}")


        gridState.values.forEach {
            it.isSelected = false
        }

        gridState.values.forEach {
            //if (gridState[x * 10 + y]?.value != 0) {
                if (it.x == x || it.y == y) {//|| it.nonet == gridState[x * 10 + y]?.nonet) {
                    it.isSelected = true
                }
            //}
        }

        subGridState?.invoke(gridState)

    }
}


class SudokuCell(
    val x: Int,
    val y: Int,
    var value: Int,
    var isSelected: Boolean,
    val nonet: Int
)