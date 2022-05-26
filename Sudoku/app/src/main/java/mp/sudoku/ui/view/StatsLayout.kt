package mp.sudoku.ui.view

import android.util.StatsLog
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import mp.sudoku.R
import mp.sudoku.model.Game
import mp.sudoku.ui.theme.BackgroundWhite
import mp.sudoku.ui.theme.NormalBlue

@Preview(showBackground = true)
@Composable
fun StatsLayout() {

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

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(
            includeBackButton = true,
            includeSettingsButton = false,
            includeGuideButton = false,
            backgroundColor = color.value
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = color.value)
                .verticalScroll(rememberScrollState())
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CircularProgressIndicator()

            Text(
                text = stringResource(R.string.won_games_perc),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(20.dp)
            )

            GamesStatsLayout()
            TimeStatsLayout()
            ScoreStatsLayout()
        }
    }
}

@Composable
fun CircularProgressIndicator(
    percentage: Float = 0.75f,
    number: Int = 100,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = NormalBlue,
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
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }

}


@Composable
fun GamesStatsLayout() {
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
            backgroundColor = Color.White
        ) {
            Column() {
                StatsRow(description = stringResource(R.string.games_played), intValue = 10)
                StatsRow(description = stringResource(R.string.games_won), intValue = 8)
                StatsRow(description = stringResource(R.string.games_lost), intValue = 2)
                StatsRow(description = stringResource(R.string.games_started), intValue = 3)

            }

        }

    }
}

@Composable
fun TimeStatsLayout() {
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
            backgroundColor = Color.White
        ) {
            Column() {
                StatsRow(description = stringResource(R.string.best_time), floatValue = 10.35f)
                StatsRow(description = stringResource(R.string.av_time), floatValue = 13.00f)
            }

        }

    }
}

@Composable
fun ScoreStatsLayout() {
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
            backgroundColor = Color.White
        ) {
            Column() {
                StatsRow(description = stringResource(R.string.best_score), intValue = 1000)
                StatsRow(description = stringResource(R.string.av_score), intValue = 1300)

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
        Text(text = description, fontSize = 15.sp, color = Color.Black)
        if (intValue >= 0)
            Text(text = intValue.toString(), fontSize = 15.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
        else
            Text(text = "$floatValue min", fontSize = 15.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
    }
    Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.width(dividerWidth.value), startIndent = 10.dp)
}

