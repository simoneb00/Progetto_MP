package mp.sudoku.ui.view.resume

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import mp.sudoku.ui.view.game.ReadOnlyGrid
import mp.sudoku.viewmodel.*

@Preview
@Composable
fun ResumeLayout() {
    val statsVM = StatisticVM(
        LocalContext
            .current.applicationContext as Application
    )

    val startedGames by statsVM.startedGames.observeAsState(listOf())   // list of all started games

    if (startedGames.isEmpty()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(stopWatch = StopWatch())
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.no_started_games_found),
                    color = if (isDarkModeOn()) Color.White else Color.Gray
                )
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(stopWatch = StopWatch())

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

}

@Composable
fun StartedGameCard(game: Game = Game()) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        /* the two following statements retrieve the grid content */
        val boardStringList: List<List<String>> = Adapter.boardPersistenceFormatToList(game.grid)
        val boardIntList: List<List<Int>> = Adapter.changeStringToInt(boardStringList)

        val activeGameVM = ActiveGameVM()
        val gameVM = GameVM(LocalContext.current.applicationContext as Application)

        Column(modifier = Modifier.padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)) {

            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.game) + " " + game.id,
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
                ReadOnlyGrid(
                    values = boardIntList,
                    activeGameVM = activeGameVM,
                    notes = Adapter.changeStringToInt(Adapter.boardPersistenceFormatToList(game.noteGrid)),
                    isReadOnly = true
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 30.dp, end = 30.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        CurrentGame.getInstance().current = game
                        ScreenRouter.navigateTo(destination = ScreenRouter.GAMEDETAILSSCREEN)
                    },
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondary)
                ) {
                    Text(
                        text = stringResource(R.string.view_details),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Button(
                    onClick = {
                        ScreenRouter.game.value = Adapter.boardPersistenceFormatToList(game.grid)
                        CurrentGame.getInstance().current = game
                        gameVM.resumeGame()
                        ScreenRouter.navigateTo(ScreenRouter.RESUMESCREEN, ScreenRouter.GAMESCREEN)
                    },
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondary)
                ) {
                    Text(
                        text = stringResource(R.string.resume),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }

        }

    }
}
