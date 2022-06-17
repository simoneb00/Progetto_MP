package mp.sudoku.ui.view.game

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.model.CurrentGame
import mp.sudoku.model.SudokuCell
import mp.sudoku.viewmodel.ActiveGameVM
import mp.sudoku.viewmodel.Adapter


@SuppressLint("MutableCollectionMutableState")
@Composable
fun Grid(
    values: List<List<Int>>,
    notes: List<List<Int>>,
    activeGameVM: ActiveGameVM,
    isReadOnly: Boolean = false,
    resume: Boolean
) {

    /* if the game has been resumed, retrieve the initial grid, else the initial grid is the given grid (values) */
    if (resume) {
        activeGameVM.initGrid(
            list = values,
            notes = notes,
            isReadOnly = isReadOnly,
            initialGrid = Adapter.changeStringToInt(
                Adapter.boardPersistenceFormatToList(
                    CurrentGame.getInstance().current?.firstGrid ?: ""
                )
            )
        )
    } else {
        activeGameVM.initGrid(
            list = values,
            notes = notes,
            isReadOnly = isReadOnly,
            initialGrid = values
        )
    }

    /* this values observes activeGameVM's value gridState */
    val gridState = rememberSaveable {
        mutableStateOf(activeGameVM.gridState, neverEqualPolicy())
    }

    /* the following makes possible to get gridState's updates */
    activeGameVM.subGridState = {
        gridState.value = it
    }


    BoxWithConstraints {
        val screenWidth = with(LocalDensity.current) {
            constraints.maxWidth.toDp()
        }

        val margin = 15

        Box(
            modifier = Modifier
                .border(width = 2.dp, color = MaterialTheme.colors.secondary)
                .size(screenWidth - margin.dp)
        ) {
            val offset = (screenWidth - margin.dp).value / 9

            /* SudokuTextFields are the updatable 'boxes' that contain the values in the grid */
            SudokuTextFields(offset, activeGameVM, gridState = gridState.value)
            /* BoardGrid creates the grid's structure */
            BoardGrid(offset)
        }
    }
}

@Composable
fun BoardGrid(offset: Float) {

    (1 until 9).forEach {

        /* the lines that divide two sub-blocks are thicker */
        val width = if (it % 3 == 0) 3.dp else 1.dp

        /* horizontal lines */
        Divider(
            modifier = Modifier
                .absoluteOffset((offset * it).dp, 0.dp)
                .fillMaxHeight()
                .width(width)
        )

        /* the lines that divide two sub-blocks are thicker */
        val height = if (it % 3 == 0) 3.dp else 1.dp

        /* vertical lines */
        Divider(
            modifier = Modifier
                .absoluteOffset(0.dp, (offset * it).dp)
                .fillMaxWidth()
                .height(height)
        )
    }
}

@Composable
fun SudokuTextFields(
    offset: Float,
    vm: ActiveGameVM,
    gridState: HashMap<Int, SudokuCell>
) {


    gridState.values.forEach { cell ->
        var text = cell.value.toString()
        var note = cell.note.toString()

        if (text == "0") text = ""
        if (note == "0") note = ""

        Box(
            modifier = Modifier
                .absoluteOffset(
                    (offset * (cell.x)).dp,
                    (offset * (cell.y)).dp
                )
                .width(offset.dp)
                .height(offset.dp)
                .background(
                    when {
                        cell.isSelected -> MaterialTheme.colors.secondaryVariant
                        cell.isInEvidence -> MaterialTheme.colors.primaryVariant
                        else -> MaterialTheme.colors.surface
                    }
                )
                .clickable {
                    if (!cell.isReadOnly)  // if the cell is readOnly (initial values cells) it is not clickable at all
                        vm.selectCell(cell.x, cell.y)
                },
            contentAlignment = if (note == "") Alignment.Center else Alignment.TopStart // notes are smaller and aligned at top start
        ) {
            if (note == "") {
                Text(
                    text = text,
                    color = if (cell.color == "Red") Color.Red else MaterialTheme.colors.onPrimary  // red color is used to show wrong values when the grid is checked
                )
            } else {
                Text(
                    text = note,
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                    fontSize = 10.sp
                )
            }
        }
    }
}
