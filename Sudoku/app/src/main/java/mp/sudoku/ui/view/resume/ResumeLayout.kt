package mp.sudoku.ui.view.resume

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.R
import mp.sudoku.model.CurrentGame
import mp.sudoku.model.Game
import mp.sudoku.ui.theme.isDarkModeOn
import mp.sudoku.ui.view.ScreenRouter
import mp.sudoku.ui.view.components.TopBar
import mp.sudoku.ui.view.game.Grid
import mp.sudoku.viewmodel.*

@Preview
@Composable
fun ResumeLayout() {
    val statsVM = StatisticVM(
        LocalContext
            .current.applicationContext as Application
    )

    val actVM = ActiveGameVM()

    val startedGames by statsVM.startedGames.observeAsState(listOf())

    if (startedGames.isEmpty()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(activeGameVM = actVM, stopWatch = StopWatch())
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.no_started_games_found),
                    color = if (isDarkModeOn()) Color.White else Color.Gray
                )
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(activeGameVM = actVM, stopWatch = StopWatch())

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                items(startedGames) { game ->
                    StartedGameCard(game)
                    //StartedGameCard1(game)
                }
            }
        }
    }

}

@Composable
fun StartedGameCard(game: Game = Game()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
        //.height(75.dp)
        backgroundColor = MaterialTheme.colors.primary,
        //border = BorderStroke(2.dp, MaterialTheme.colors.secondary),
        //shape = RoundedCornerShape(10.dp)
    ) {

        val boardStringList: List<List<String>> = Adapter.boardPersistenceFormatToList(game.grid)
        val boardIntList: List<List<Int>> = Adapter.changeStringToInt(boardStringList)
        val gameVM = GameVM(
            LocalContext
                .current.applicationContext as Application
        )
        val activeGameVM = ActiveGameVM()


        Column(modifier = Modifier.padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)) {

            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Game ${game.id}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Box(
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp, top = 10.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Grid(values = boardIntList, activeGameVM = activeGameVM, isReadOnly = true)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 30.dp, end = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        ScreenRouter.game = game
                        ScreenRouter.navigateTo(destination = ScreenRouter.GAMEDETAILSSCREEN)
                    },
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                    //shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "View Details", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = {
                        CurrentGame.getInstance().current = game
                        ScreenRouter.navigateTo(ScreenRouter.RESUMESCREEN, ScreenRouter.GAMESCREEN)
                    },
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                    //shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Resume", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }

            }

        }

    }
}

@Composable
fun StartedGameCard1(game: Game = Game()) {

    val boardStringList: List<List<String>> = Adapter.boardPersistenceFormatToList(game.grid)
    val boardIntList: List<List<Int>> = Adapter.changeStringToInt(boardStringList)
    val gameVM = GameVM(
        LocalContext
            .current.applicationContext as Application
    )
    val activeGameVM = ActiveGameVM()

    Box(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, top = 10.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Grid(values = boardIntList, activeGameVM = activeGameVM, isReadOnly = true)
    }

    Box(
        modifier = Modifier
            .padding(start = 32.dp, end = 32.dp)
            .fillMaxWidth()
        //.height(75.dp)
        ,
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Difficulty: ", modifier = Modifier.padding(start = 5.dp))
                    Text(
                        text = game.difficulty,
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Score: ", modifier = Modifier.padding(start = 5.dp))
                    Text(
                        text = game.score.toString(),
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Timer: ", modifier = Modifier.padding(start = 5.dp))
                    Text(
                        text = game.timer,
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Last Update: ", modifier = Modifier.padding(start = 5.dp))
                    Text(
                        text = game.lastUpdate,
                        color = Color.Gray,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                }
            }

            Button(
                onClick = {
                    CurrentGame.getInstance().current = game
                    ScreenRouter.navigateTo(ScreenRouter.RESUMESCREEN, ScreenRouter.GAMESCREEN)
                },
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Resume", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}