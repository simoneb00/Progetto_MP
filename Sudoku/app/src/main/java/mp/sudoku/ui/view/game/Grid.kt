
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.model.SudokuCell
import mp.sudoku.viewmodel.ActiveGameVM


@SuppressLint("MutableCollectionMutableState")
@Composable
fun Grid(
    values: List<List<Int>>,
    activeGameVM: ActiveGameVM,
    isReadOnly: Boolean = false
) {

    activeGameVM.initGrid(values, isReadOnly)

    val gridState = rememberSaveable {
        mutableStateOf(activeGameVM.gridState, neverEqualPolicy())
    }

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
            SudokuTextFields(offset, activeGameVM, gridState = gridState.value)
            BoardGrid(offset)
        }
    }
}

@Composable
fun BoardGrid(offset: Float) {

    (1 until 9).forEach {
        val width = if (it % 3 == 0) 3.dp else 1.dp
        Divider(
            modifier = Modifier
                .absoluteOffset((offset * it).dp, 0.dp)
                .fillMaxHeight()
                .width(width)
        )

        val height = if (it % 3 == 0) 3.dp else 1.dp
        Divider(
            modifier = Modifier
                .absoluteOffset(0.dp, (offset * it).dp)
                .fillMaxWidth()
                .height(height)
        )
    }
}

@Composable
fun SudokuTextFields(offset: Float, vm: ActiveGameVM, gridState: HashMap<Int, SudokuCell>) {


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
                    //if (!cell.isReadOnly)
                        vm.selectCell(cell.x, cell.y)
                },
            contentAlignment = if (note == "") Alignment.Center else Alignment.TopStart
        ) {
            if (note == "") {
                Text(
                    text = text, color = MaterialTheme.colors.onPrimary
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
