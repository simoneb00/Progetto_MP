package mp.sudoku.ui.view

import android.app.Application
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mp.sudoku.R
import mp.sudoku.ui.theme.logoId
import mp.sudoku.ui.view.components.NavigationButton
import mp.sudoku.ui.view.components.TopBar
import mp.sudoku.viewmodel.ActiveGameVM
import mp.sudoku.viewmodel.StatisticVM
import mp.sudoku.viewmodel.StopWatch

@Preview(showBackground = true)
@Composable
fun HomeLayout() {

    Column(
        modifier = Modifier
            .fillMaxHeight()
        //.verticalScroll(rememberScrollState())
    ) {
        TopBar(
            includeBackButton = false,
            includeSettingsButton = true,
            includeGuideButton = true,
            stopWatch = StopWatch()
        )
        Logo()
        Buttons()
    }
}


@Composable
fun Logo() {

    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        val image: Painter = painterResource(id = logoId.value)
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .size(150.dp)
                .padding(vertical = 20.dp)
        )
    }
}

@Preview
@Composable
fun Buttons() {

    val statsVM = StatisticVM(LocalContext.current.applicationContext as Application)
    val startedGames by statsVM.startedGames.observeAsState(listOf())

    val maximumHeight = if (startedGames.isNotEmpty()) 300.dp else 210.dp

    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val height = animateDpAsState(
        targetValue = if (animationPlayed) maximumHeight else 0.dp,
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
        if (startedGames.isNotEmpty()) {
            NavigationButton(
                text = stringResource(R.string.resume),
                destination = ScreenRouter.RESUMESCREEN
            )
        }
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


