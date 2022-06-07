package mp.sudoku.ui.view.resume

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.model.Game
import mp.sudoku.ui.view.components.TopBar
import mp.sudoku.ui.view.game.Grid
import mp.sudoku.viewmodel.ActiveGameVM
import mp.sudoku.viewmodel.Adapter
import mp.sudoku.viewmodel.StopWatch

@Composable
fun GameDetailsLayout(game: Game) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(activeGameVM = ActiveGameVM(), stopWatch = StopWatch())
        Title(game)
        GridPreview(game)
        DetailsRow(game)
    }
}

@Composable
fun DetailsRow(game: Game) {

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    var height = animateDpAsState(
        targetValue = if (animationPlayed) 120.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 0
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp) //, end = 20.dp, start = 20.dp)
                .height(height.value)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                border = BorderStroke(0.5.dp, MaterialTheme.colors.secondary)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Difficulty:", modifier = Modifier.padding(start = 5.dp))
                    Text(
                        text = game.difficulty,
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                border = BorderStroke(0.5.dp, MaterialTheme.colors.secondary)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Last Update:", modifier = Modifier.padding(start = 5.dp))
                    Text(
                        text = game.lastUpdate,
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                border = BorderStroke(0.5.dp, MaterialTheme.colors.secondary)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Score:", modifier = Modifier.padding(start = 5.dp))
                    Text(
                        text = game.score.toString(),
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                border = BorderStroke(0.5.dp, MaterialTheme.colors.secondary)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Timer:", modifier = Modifier.padding(start = 5.dp))
                    Text(
                        text = game.timer,
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GridPreview(game: Game) {
    val boardStringList: List<List<String>> = Adapter.boardPersistenceFormatToList(game.grid)
    val boardIntList: List<List<Int>> = Adapter.changeStringToInt(boardStringList)
    val activeGameVM = ActiveGameVM()

    Box(
        modifier = Modifier
            .padding(start = 50.dp, end = 50.dp, top = 20.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Grid(values = boardIntList, activeGameVM = activeGameVM, isReadOnly = true)
    }
}

@Composable
fun Title(game: Game) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Game ${game.id}",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.secondary
        )
    }
}