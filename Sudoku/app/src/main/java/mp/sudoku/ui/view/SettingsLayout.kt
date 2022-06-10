package mp.sudoku.ui.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.R
import mp.sudoku.ui.theme.invertTheme
import mp.sudoku.ui.view.components.TopBar
import mp.sudoku.viewmodel.ActiveGameVM
import mp.sudoku.viewmodel.SettingsVM
import mp.sudoku.viewmodel.StopWatch

@Preview(showBackground = true)
@Composable
fun SettingsLayout() {

    val settingsVM = SettingsVM(LocalContext.current.applicationContext)

    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        TopBar(
            includeBackButton = true,
            includeSettingsButton = false,
            includeGuideButton = false,
            stopWatch = StopWatch()
        )

        SettingsTitle()
        Settings(settingsVM)
    }
}


@Composable
fun SettingsTitle() {
    Row(
        horizontalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.secondary
        )
    }
}

@Composable
fun Settings(settingsVM: SettingsVM) {

    val timerSetting = settingsVM.getTimerSetting()
    val scoreSetting = settingsVM.getScoreSetting()
    val hintsSetting = settingsVM.getHintsSetting()
    val darkModeSetting = settingsVM.getDarkModeSetting()

    /* divider animation */
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

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 5.dp, bottom = 5.dp)
    ) {

        Text(
            text = stringResource(R.string.game_settings),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.padding(start = 10.dp, bottom = 2.dp)
        )

        SettingCard(
            title = stringResource(R.string.show_timer),
            onCheckedChange = {
                            settingsVM.updateShowTimerSetting(!timerSetting)
            },
            initialState = timerSetting
        )
        SettingCard(
            title = stringResource(R.string.show_score),
            onCheckedChange = {
                              settingsVM.updateShowScoreSetting(!scoreSetting)
            },
            initialState = scoreSetting
        )
        SettingCard(
            title = stringResource(R.string.hints),
            description = stringResource(R.string.hints_setting_desc),
            onCheckedChange = {
                              settingsVM.updateEnableHintsSetting(!hintsSetting)
            },
            initialState = hintsSetting
        )

        Divider(
            color = MaterialTheme.colors.secondary,
            thickness = 1.dp,
            modifier = Modifier
                .width(dividerWidth.value)
                .padding(top = 10.dp, bottom = 10.dp)
        )

        Text(
            text = stringResource(R.string.theme),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 2.dp)
        )

        SettingCard(
            title = stringResource(R.string.dark_theme),
            initialState = darkModeSetting,
            onCheckedChange = {
                settingsVM.updateEnableDarkThemeSetting(!darkModeSetting)
                invertTheme()
            }
        )
    }
}

@Composable
fun SettingCard(
    title: String,
    description: String = "",           // optional: by default, the description is empty
    initialState: Boolean = true,       // optional: by default, the switch is activated
    onCheckedChange: () -> Unit = {}    // optional: by default, the switch does not trigger any action
) {
    Card(
        modifier = Modifier.height(60.dp),
        elevation = 0.dp    // to make the border of the card invisible
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Column {
                Text(text = title, fontSize = 15.sp)
                Text(text = description, fontSize = 12.sp, color = Color.Gray)
            }

            val checkedState =
                remember { mutableStateOf(initialState) }    // initial state of the switch

            /* to update the switch and to execute the lambda function passed as parameter */
            val update: ((Boolean)) -> Unit = { boolean ->
                checkedState.value = boolean
                onCheckedChange()
            }

            Switch(
                checked = checkedState.value,
                onCheckedChange = update,
                colors = SwitchDefaults.colors(MaterialTheme.colors.secondary)
            )
        }
    }
}



