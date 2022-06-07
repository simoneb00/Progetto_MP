package mp.sudoku.ui.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.ui.view.ScreenRouter
import mp.sudoku.viewmodel.Adapter

@Composable
fun NavigationButton(text: String, destination: Int) {
    Button(
        onClick = {
            ScreenRouter.navigateTo(destination = destination)
            println(Adapter.boardListToPersistenceFormat(
                listOf(
                    listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
                    listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
                    listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
                    listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
                    listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
                    listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
                    listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
                    listOf(1, 2, 3, 4, 5, 6, 7, 8, 9),
                    listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
            )))
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
        modifier = Modifier.size(height = 50.dp, width = 250.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colors.secondary)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 20.sp
        )
    }
}

@Composable
fun DifficultyButton(text: String, destination: Int) {
    Button(
        onClick = {
            ScreenRouter.difficulty.value = text; ScreenRouter.navigateTo(destination = destination)
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background),
        modifier = Modifier.size(height = 50.dp, width = 250.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colors.secondary)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 20.sp
        )
    }
}