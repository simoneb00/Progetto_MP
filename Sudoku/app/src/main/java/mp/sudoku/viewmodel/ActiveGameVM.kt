package mp.sudoku.viewmodel

import mp.sudoku.model.CurrentGame
import mp.sudoku.model.SudokuCell

class ActiveGameVM {

    internal val gridState: HashMap<Int, SudokuCell> = HashMap()
    internal var subGridState: ((HashMap<Int, SudokuCell>) -> Unit)? =
        null   // this is useful to commit changes to the view (Grid)
    internal var subGridState1: ((HashMap<Int, SudokuCell>) -> Unit)? =
        null  // this is useful to commit changes to the view (GameLayout)

    internal var isCompleted = false                                // true if grid is full
    internal var subCompletedState: ((Boolean) -> Unit)? =
        null     // this is useful to commit changes to the view (GameLayout, to show "Check" button)

    internal var notesMode = false   // true if user is inserting notes

    internal var buttonsNumbers: MutableList<Int> =
        mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)    // buttons to insert numbers in the grid
    internal var subButtonsNumbers: ((MutableList<Int>) -> Unit)? =
        null                        // to commit changes to the view (GameLayout, to know which buttons to show)


    /* notesState - initialized empty - keeps track of the notes in the grid */
    internal var notesState: MutableList<MutableList<Int>> = mutableListOf(
        mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
    )


    fun initGrid(
        list: List<List<Int>>,
        initialGrid: List<List<Int>>,
        notes: List<List<Int>>,
        isReadOnly: Boolean
    ) {

        for (i in list.indices) {               // i = number of row
            for (j in list[i].indices) {        // j = number of element in the row
                /*
                *   The key can be seen as the matrix indexes (row, col).
                *
                *   For example, the second element in the first row will have key 12,
                *   the fourth element of the seventh row will have key 74, and so on.
                *
                */
                if (!isReadOnly) {
                    gridState[(j * 10 + i)] = SudokuCell(
                        x = j,
                        y = i,
                        value = list[i][j],
                        isSelected = false,
                        nonet = findNonet(j, i),            // a nonet is a 3x3 sub-block
                        isInEvidence = false,                  // the cell is on focus if it's placed on the same row/column/nonet of the selected cell
                        isReadOnly = (initialGrid[i][j] != 0),     // the read only cells are those present in the initial grid
                        note = 0,
                        color = "Black"
                    )
                } else {
                    gridState[(j * 10 + i)] = SudokuCell(
                        x = j,
                        y = i,
                        value = list[i][j],
                        isSelected = false,
                        nonet = findNonet(j, i),
                        isInEvidence = false,
                        isReadOnly = true,
                        note = 0,
                        color = "Black"
                    )
                }
            }
        }


        /* populating grid with notes (if any) */
        for (i in notes.indices) {
            for (j in notes[i].indices) {
                if (gridState[j * 10 + i]?.value == 0) {
                    gridState[(j * 10 + i)] = SudokuCell(
                        x = j,
                        y = i,
                        value = 0,
                        isSelected = false,
                        nonet = findNonet(j, i),
                        isInEvidence = false,
                        isReadOnly = false,
                        note = notes[i][j],
                        color = "Black"
                    )

                    notesState[i][j] =
                        notes[i][j]  // updating notesState, in order to keep track of notes
                }
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
                    notesState[it.y][it.x] = value

                    /* the following updates CurrentGame's notes status */
                    CurrentGame.getInstance().current?.noteGrid = Adapter.boardListToPersistenceFormat(notesState)

                } else {                // if notes mode is off and the user inserts a value on the cell, the previously present note (if any) is cancelled
                    it.note = 0
                    it.value = value
                }

            }
        }

        /* the following updates CurrentGame's grid status */
        CurrentGame.getInstance().current?.grid = Adapter.boardListToPersistenceFormat(Adapter.hashMapToList(gridState))
        subGridState?.invoke(gridState)     // commit changes to the view (Grid)
        subGridState1?.invoke(gridState)     // commit changes to the view (GameLayout)
        if (checkIfFull()) subCompletedState?.invoke(true)      // if the grid is full, inform the view to show the "Check" button
    }


    fun getHint(): Int {
        var hint = 0
        try {
            hint = CurrentGame.getInstance().solution?.get(getSelectedCellY())!![getSelectedCellX()]    // the hint is taken from the solved grid
            updateGrid(hint)    // showing hint on the grid
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return hint
    }


    private fun getSelectedCellX(): Int {
        var x = 0
        gridState.values.forEach {
            if (it.isSelected) {        // the following operations are going to be applied only to the selected cell
                x = it.x
            }
        }
        return x
    }

    private fun getSelectedCellY(): Int {
        var y = 0
        gridState.values.forEach {
            if (it.isSelected) {        // the following operations are going to be applied only to the selected cell
                y = it.y
            }
        }
        return y
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

        /* restore every value on the number buttons bar and commit changes to the view */
        this.buttonsNumbers = mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        subButtonsNumbers?.invoke(buttonsNumbers)

        gridState.values.forEach {
            /* the following two statements 'deselect' every cell */
            it.isSelected = false
            it.isInEvidence = false

            if (it.x == x && it.y == y) {
                it.isSelected = true        // mark the (x, y) cell as selected
            }
            if (it.x == x || it.y == y || it.nonet == gridState[x * 10 + y]?.nonet)
                it.isInEvidence = true         // put every cell on the same row/column/nonet in evidence

            if (gridState[x * 10 + y]?.value != 0) {
                if (it.value == gridState[x * 10 + y]?.value)
                    it.isInEvidence = true      // put every cell containing the same value in evidence
            }

            /* remove from the number buttons every value contained in the selected nonet */
            if (it.nonet == gridState[x * 10 + y]?.nonet)
                updateNumberButtons(valToRemove = it.value)


        }
        subGridState?.invoke(gridState)     // commit changes to the view (Grid)
        subGridState1?.invoke(gridState)     // commit changes to the view (GameLayout)
    }

    fun cancelCell() {

        gridState.values.forEach {
            if (it.isSelected) {
                // whether the cell contains a note or a value, its content is cancelled
                it.note = 0
                it.value = 0
                notesState[it.y][it.x] = 0      // updating notes status
            }
        }

        subGridState?.invoke(gridState)     // commit changes to the view (Grid)
        subGridState1?.invoke(gridState)     // commit changes to the view (GameLayout)
    }

    fun incrementCounter() {
        val game = CurrentGame.getInstance().getCurrent()
        try {
            game!!.hintCounter++

            /* score decreased by 5 for each hint requested (minimum score is 5) */
            if (game.score - 5 > 0)
                game.score = game.score - 5

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getScore(): Int {
        return CurrentGame.getInstance().getCurrent()?.score ?: 100
    }


    fun checkGrid(grid: HashMap<Int, SudokuCell>): Boolean {
        val linearSolution = CurrentGame.getInstance().solution     // solved grid
        val linearGrid = Adapter.hashMapToList(grid)                // input grid

        val isCorrect = linearSolution == linearGrid

        return if (isCorrect)
            isCorrect
        else {
            /* marking wrong values in red */
            grid.values.forEach { cell ->
                if (cell.value != linearSolution?.get(cell.y)?.get(cell.x))
                    cell.color = "Red"
            }

            subGridState?.invoke(gridState)     // commit changes to the view (Grid)
            subGridState1?.invoke(gridState)     // commit changes to the view (GameLayout)

            isCorrect
        }
    }


    private fun updateNumberButtons(valToRemove: Int) {
        this.buttonsNumbers.remove(valToRemove)             // removing button #valToRemove from the buttons in the view
        subButtonsNumbers?.invoke(this.buttonsNumbers)
    }
}

