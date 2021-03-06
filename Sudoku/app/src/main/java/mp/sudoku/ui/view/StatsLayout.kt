package mp.sudoku.ui.view

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.R
import mp.sudoku.ui.view.components.TopBar
import mp.sudoku.viewmodel.StatisticVM
import mp.sudoku.viewmodel.StopWatch

@SuppressLint("UnrememberedMutableState", "NewApi")
@Preview(showBackground = true)
@Composable
fun StatsLayout() {
    /* parameters initialization */
    val viewModel = StatisticVM(
        LocalContext
            .current.applicationContext as Application
    )

    val allGames by viewModel.allGames.observeAsState(listOf())
    val finishedGames by viewModel.finishedGames.observeAsState(listOf())

    val numFinishedGames = finishedGames.size
    val numGames = allGames.size

    var bestTime: String = StatisticVM.getBestTime(finishedGames)
    if (bestTime.isEmpty()) bestTime = stringResource(R.string.no_games_found)
    var averageTime: String = StatisticVM.getAvgTime(finishedGames)
    if (averageTime.isEmpty()) averageTime = stringResource(R.string.no_games_found)


    val bestScore: Float = StatisticVM.getMaxScore(finishedGames)
    val averageScore: Float = StatisticVM.getAvgScore(finishedGames)


    Column(modifier = Modifier.fillMaxSize()) {

        TopBar(
            includeSettingsButton = false,
            includeGuideButton = false,
            stopWatch = StopWatch()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())      // to make the column scrollable
                .padding(top = 10.dp)
        ) {

            /* percentage indicator */
            val percentage: Float = numFinishedGames.toFloat() / numGames
            CircularProgressIndicator(percentage = percentage)

            /* percentage indicator description */
            Text(
                text = stringResource(R.string.won_games_perc),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                //color = Color.Black,
                modifier = Modifier.padding(20.dp)
            )

            GamesStatsLayout(numGames = numGames, numFinishedGames = numFinishedGames)
            TimeStatsLayout(bestTime = bestTime, averageTime = averageTime)
            ScoreStatsLayout(bestScore = bestScore, averageScore = averageScore)
        }
    }
}

@Composable
fun CircularProgressIndicator(
    percentage: Float,
    number: Int = 100,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = MaterialTheme.colors.secondary,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = color,
                -90f,
                360 * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        Text(
            text = (curPercentage.value * number).toInt().toString() + "%",
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }

}


@Composable
fun GamesStatsLayout(
    numGames: Int,
    numFinishedGames: Int
) {
    Column(
        modifier = Modifier.padding(top = 20.dp, start = 5.dp, end = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.games),
                fontSize = 15.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, top = 5.dp, end = 5.dp),
            shape = RoundedCornerShape(3.dp),
            //backgroundColor = Color.White
        ) {
            Column {
                StatsRow(
                    description = stringResource(R.string.games_played),
                    games = numGames
                )
                StatsRow(
                    description = stringResource(R.string.games_won),
                    games = numFinishedGames
                )
            }

        }

    }
}

@Composable
fun TimeStatsLayout(bestTime: String, averageTime: String) {
    Column(
        modifier = Modifier.padding(top = 20.dp, start = 5.dp, end = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.time),
                fontSize = 15.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, top = 5.dp, end = 5.dp),
            shape = RoundedCornerShape(3.dp),
        ) {
            Column {
                StatsRow(
                    description = stringResource(R.string.best_time),
                    time = bestTime
                )
                StatsRow(
                    description = stringResource(R.string.av_time),
                    time = averageTime
                )
            }

        }

    }
}

@Composable
fun ScoreStatsLayout(bestScore: Float, averageScore: Float) {
    Column(
        modifier = Modifier.padding(top = 20.dp, start = 5.dp, end = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.score),
                fontSize = 15.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, top = 5.dp, bottom = 20.dp, end = 5.dp),
            shape = RoundedCornerShape(3.dp),
            //backgroundColor = MaterialTheme.colors.surface
        ) {
            Column {
                StatsRow(description = stringResource(R.string.best_score), score = bestScore)
                StatsRow(description = stringResource(R.string.av_score), score = averageScore)

            }

        }

    }
}

@Composable
fun StatsRow(description: String, games: Int = -1, time: String = "", score: Float = -1f) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val dividerWidth = animateDpAsState(
        targetValue = if (animationPlayed) 400.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 0
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(top = 2.dp, start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = description,
            fontSize = 15.sp
        )

        if (games != -1 && time.isEmpty() && score == -1f) {
            Text(
                text = games.toString(),
                fontSize = 15.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
        }

        else if (time.isNotEmpty() && games == -1 && score == -1f) {
            Text(
                text = time,
                fontSize = 15.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
        }

        else if (score != -1f && time.isEmpty() && games == -1) {
            Text(
                text = score.toString(),
                fontSize = 15.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
        }

    }
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier.width(dividerWidth.value),
        startIndent = 10.dp
    )
}


