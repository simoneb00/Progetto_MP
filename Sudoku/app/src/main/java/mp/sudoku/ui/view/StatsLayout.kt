package mp.sudoku.ui.view

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.animation.animateColorAsState
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
import mp.sudoku.ui.theme.BackgroundWhite
import mp.sudoku.viewmodel.StatisticVM

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun StatsLayout() {

    /* parameters initialization */
    val viewModel = StatisticVM(
        LocalContext
            .current.applicationContext as Application
    )
    val numWonGames: MutableState<Int> = remember { mutableStateOf(0) }
    val numGames: MutableState<Int> = remember { mutableStateOf(0) }

    viewModel.getWonGames(numWonGames)
    viewModel.getGames(numGames)

    val numStartedGames: Int = 0    /* TODO */
    val bestTime: Float = 0f        /* TODO */
    val averageTime: Float = 0f     /* TODO */
    val bestScore: Int = 0          /* TODO */
    val averageScore: Int = 0       /* TODO */

/*
    /* background color transition animation */

    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val color = animateColorAsState(
        targetValue = if (animationPlayed) BackgroundWhite else Color.White,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 0
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    /* end of animation */

 */

    Column(modifier = Modifier.fillMaxSize()) {

        TopBar(
            includeSettingsButton = false,
            includeGuideButton = false,
            //backgroundColor = color.value
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                //.background(color = color.value)
                //.background(color = BackgroundWhite)
                .verticalScroll(rememberScrollState())      // to make the column scrollable
                .padding(top = 10.dp)
        ) {

            /* percentage indicator */
            val percentage: Float = numWonGames.value.toFloat() / numGames.value
            CircularProgressIndicator(percentage = percentage)

            /* percentage indicator description */
            Text(
                text = stringResource(R.string.won_games_perc),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                //color = Color.Black,
                modifier = Modifier.padding(20.dp)
            )

            GamesStatsLayout(numStartedGames = numStartedGames, numGames = numGames.value, numWonGames = numWonGames.value)
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
            //color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }

}


@Composable
fun GamesStatsLayout(
    numGames: Int,
    numWonGames: Int,
    numStartedGames: Int
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
            Column() {
                StatsRow(description = stringResource(R.string.games_played), intValue = numGames)
                StatsRow(description = stringResource(R.string.games_won), intValue = numWonGames)
                StatsRow(description = stringResource(R.string.games_lost), intValue = numGames - numWonGames)
                StatsRow(description = stringResource(R.string.games_started), intValue = numStartedGames)

            }

        }

    }
}

@Composable
fun TimeStatsLayout(bestTime: Float, averageTime: Float) {
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
            //backgroundColor = MaterialTheme.colors.surface
        ) {
            Column() {
                StatsRow(description = stringResource(R.string.best_time), floatValue = bestTime)
                StatsRow(description = stringResource(R.string.av_time), floatValue = averageTime)
            }

        }

    }
}

@Composable
fun ScoreStatsLayout(bestScore: Int, averageScore: Int) {
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
            Column() {
                StatsRow(description = stringResource(R.string.best_score), intValue = bestScore)
                StatsRow(description = stringResource(R.string.av_score), intValue = averageScore)

            }

        }

    }
}

@Composable
fun StatsRow(description: String, intValue: Int = -1, floatValue: Float = -1f) {


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
        Text(text = description,
            fontSize = 15.sp,
            //color = Color.Black
            )
        if (intValue >= 0)
            Text(text = intValue.toString(), fontSize = 15.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
        else
            Text(text = "$floatValue min", fontSize = 15.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
    }
    Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.width(dividerWidth.value), startIndent = 10.dp)
}

