@file:OptIn(ExperimentalFoundationApi::class)

package mp.sudoku.ui.view.game

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mp.sudoku.viewmodel.ActiveGameVM
import mp.sudoku.viewmodel.SudokuCell

@Preview
@Composable
fun Grid(
    values: List<List<Int>> = listOf(
        listOf(1, 0, 0, 0, 0, 0, 2, 0, 0),
        listOf(0, 2, 0, 0, 0, 0, 0, 9, 0),
        listOf(0, 0, 3, 0, 0, 0, 0, 0, 0),
        listOf(5, 0, 0, 4, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 5, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 6, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 7, 0, 0),
        listOf(0, 6, 0, 0, 0, 0, 0, 8, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 9),
    )
) {

    val activeGameVM = ActiveGameVM()
    activeGameVM.initGrid(values)

    var gridState by remember { mutableStateOf(activeGameVM.gridState, neverEqualPolicy()) }

    activeGameVM.subGridState = {
        gridState = it
    }

    BoxWithConstraints() {
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
            SudokuTextFields(offset, activeGameVM, gridState = gridState)
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

        if (text == "0") text = ""

        Box(
            modifier = Modifier
                .absoluteOffset(
                    (offset * (cell.x)).dp,
                    (offset * (cell.y)).dp
                )
                .width(offset.dp)
                .height(offset.dp)
                .background(
                    if (cell.isSelected) Color.LightGray else Color.White
                )
                .clickable {
                    vm.selectCell(cell.x, cell.y)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center
            )
        }
    }
}
