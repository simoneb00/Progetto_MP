package mp.sudoku.ui.view

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.model.Game
import mp.sudoku.ui.view.components.TopBar
import mp.sudoku.viewmodel.StatisticVM

@Preview
@Composable
fun ResumeLayout() {
    val statsVM = StatisticVM(
        LocalContext
            .current.applicationContext as Application
    )

    val startedGames by statsVM.startedGames.observeAsState(listOf())

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
        ) {
            items(startedGames) { game ->
                StartedGameCard(game)
            }
        }
    }

}

@Composable
fun StartedGameCard(game: Game = Game()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        //.height(75.dp)
        ,
        backgroundColor = MaterialTheme.colors.primary,
        border = BorderStroke(2.dp, MaterialTheme.colors.secondary),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)) {
            Row() {
                Text(
                    text = "Game ${game.id}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp)
            ) {
                Column() {
                    Text(text = "Difficulty:")
                    Text(text = "Last Update:")
                    Text(text = "Score:")
                    Text(text = "Timer:")
                }

                Column() {
                    Text(text = game.difficulty, color = Color.Gray)
                    Text(text = game.lastUpdate, color = Color.Gray)
                    Text(text = game.score.toString(), color = Color.Gray)
                    Text(text = game.timer, color = Color.Gray)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        ScreenRouter.game = game
                        ScreenRouter.navigateTo(destination = ScreenRouter.GAMEDETAILSSCREEN)
                    },
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = " View details", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }
            }

        }

    }
}