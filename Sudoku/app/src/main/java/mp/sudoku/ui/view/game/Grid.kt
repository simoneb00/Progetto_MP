@file:OptIn(ExperimentalFoundationApi::class)

package mp.sudoku.ui.view.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun Grid(
    values: List<MutableState<Int>> = listOf(
        mutableStateOf(1),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(2),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(3),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(4),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
        mutableStateOf(0),
    )
) {
    /*
    Column {
        GridRow(numbers = listOf(0, 0, 1, 0, 2, 0, 0, 0, 3))
        GridRow(numbers = listOf(0, 0, 1, 0, 2, 0, 0, 0, 3))
        GridRow(numbers = listOf(0, 0, 1, 0, 2, 0, 0, 0, 3))
        GridRow(numbers = listOf(0, 0, 1, 0, 2, 0, 0, 0, 3))
        GridRow(numbers = listOf(0, 0, 1, 0, 2, 0, 0, 0, 3))
        GridRow(numbers = listOf(0, 0, 1, 0, 2, 0, 0, 0, 3))
        GridRow(numbers = listOf(0, 0, 1, 0, 2, 0, 0, 0, 3))
        GridRow(numbers = listOf(0, 0, 1, 0, 2, 0, 0, 0, 3))
        GridRow(numbers = listOf(0, 0, 1, 0, 2, 0, 0, 0, 3))
    }
     */

    LazyVerticalGrid(
        cells = GridCells.Fixed(9),
        modifier = Modifier.padding(start = 15.dp, end = 15.dp)
    ) {
        items(values) {
            Card(
                border = BorderStroke(0.5.dp, color = Color.Black),
                modifier = Modifier
                    .size(40.dp)
                    .clickable { },
                shape = CutCornerShape(0.dp)
            ) {
                val value = it.value
                if (value == 0)
                    Text(text = "", textAlign = TextAlign.Center)
                else
                    Text(text = "$value", textAlign = TextAlign.Center)
            }
        }
    }

}


@Preview
@Composable
fun GridRow(
    numbers: List<Int> = (1..9).toList()
) {

    LazyRow() {
        items(numbers) {
            Card(
                border = BorderStroke(0.5.dp, color = Color.Black),
                modifier = Modifier
                    .size(40.dp)
                    .clickable { },
                shape = CutCornerShape(0.dp)
            ) {
                val value = it
                if (value == 0)
                    Text(text = "", textAlign = TextAlign.Center)
                else
                    Text(text = "$value", textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubGrid(
    columnCount: Int = 3,
    rowCount: Int = 3,
    numbers: List<Int> = (1..9).toList()
) {

    Surface(border = BorderStroke(2.dp, color = Color.Black)) {
        LazyVerticalGrid(cells = GridCells.Fixed(3)) {
            items(numbers.size) {
                Card(
                    border = BorderStroke(0.5.dp, color = Color.Black),
                    modifier = Modifier.size(height = 40.dp, width = 20.dp),
                    shape = CutCornerShape(0.dp)
                ) {
                    val value = it + 1
                    Text(text = "$value", textAlign = TextAlign.Center)
                }
            }
        }
    }
}