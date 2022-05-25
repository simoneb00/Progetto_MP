package mp.sudoku.ui.view

import android.provider.Settings.System.getString
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mp.sudoku.R

@Preview(showBackground = true)
@Composable
fun HomeLayout() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        TopBar(
            includeBackButton = false,
            includeGuideButton = true,
            includeSettingsButton = true
        )
        Logo()
        Buttons()
    }
}


@Composable
fun Logo() {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        val image: Painter = painterResource(id = R.drawable.sudoku_logo)
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .size(150.dp)
                .padding(vertical = 20.dp)
        )
    }
}

@Composable
fun Buttons() {
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val height = animateDpAsState(
        targetValue = if (animationPlayed) 300.dp else 0.dp,
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
            .padding(top = 100.dp)
            .height(height.value),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationButton(
            text = stringResource(R.string.new_game),
            destination = ScreenRouter.DIFFICULTYSCREEN
        )
        NavigationButton(
            text = stringResource(R.string.resume),
            destination = ScreenRouter.HOMESCREEN
        )
        NavigationButton(
            text = stringResource(R.string.statistics),
            destination = ScreenRouter.STATSSCREEN
        )
        NavigationButton(
            text = stringResource(R.string.settings),
            destination = ScreenRouter.SETTINGSCREEN
        )
    }
}


