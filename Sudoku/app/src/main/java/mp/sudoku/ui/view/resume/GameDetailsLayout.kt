package mp.sudoku.ui.view.resume

import android.app.Application
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.model.CurrentGame
import mp.sudoku.model.Game
import mp.sudoku.ui.view.ScreenRouter
import mp.sudoku.ui.view.components.TopBar
import mp.sudoku.ui.view.game.Grid
import mp.sudoku.ui.view.game.ReadOnlyGrid
import mp.sudoku.viewmodel.ActiveGameVM
import mp.sudoku.viewmodel.Adapter
import mp.sudoku.viewmodel.GameVM
import mp.sudoku.viewmodel.StopWatch

@Composable
fun GameDetailsLayout(game: Game) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(activeGameVM = ActiveGameVM(), stopWatch = StopWatch())
        Title(game)
        GridPreview(game)
        DetailsRow(game)
        ButtonsRow(game)
        /*
        Button(onClick = {
            CurrentGame.getInstance().current = game
            ScreenRouter.navigateTo(ScreenRouter.RESUMESCREEN, ScreenRouter.GAMESCREEN)
        }) {
            Text(text = "Resume")
        }

         */
    }
}

@Composable
fun ButtonsRow(game: Game) {

    val gameVM = GameVM(LocalContext.current.applicationContext as Application)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = {
                gameVM.deleteOne(game)
                ScreenRouter.navigateTo(destination = ScreenRouter.RESUMESCREEN)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "",
                tint = MaterialTheme.colors.secondary
            )
        }
        Button(
            onClick = {
                CurrentGame.getInstance().current = game
                ScreenRouter.navigateTo(ScreenRouter.RESUMESCREEN, ScreenRouter.GAMESCREEN)
            },
            border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Resume")
        }
    }
}

@Composable
fun DetailsRow(game: Game) {

    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val height = animateDpAsState(
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
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp) //, end = 20.dp, start = 20.dp)
                .height(height.value)
        ) {
            DetailRowCard(name = "Difficulty", value = game.difficulty)
            DetailRowCard(name = "Last Update", value = game.lastUpdate)
            DetailRowCard(name = "Score", value = game.score.toString())
            DetailRowCard(name = "Timer", value = game.timer)
        }
    }
}

@Composable
fun DetailRowCard(name: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        border = BorderStroke(0.2.dp, MaterialTheme.colors.secondary)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = name, modifier = Modifier.padding(start = 5.dp))
            Text(
                text = value,
                color = Color.Gray,
                modifier = Modifier.padding(end = 5.dp)
            )
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
        ReadOnlyGrid(values = boardIntList, activeGameVM = activeGameVM, isReadOnly = true)
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