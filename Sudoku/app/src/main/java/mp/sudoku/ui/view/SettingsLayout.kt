package mp.sudoku.ui.view

import android.text.method.DateKeyListener
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import mp.sudoku.R
import mp.sudoku.ui.theme.DarkBlue
import mp.sudoku.ui.theme.NormalBlue
import mp.sudoku.ui.theme.darkModeOn

@Preview(showBackground = true)
@Composable
fun SettingsLayout() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        TopBar(
            includeBackButton = true,
            includeSettingsButton = false,
            includeGuideButton = false
        )

        SettingsTitle()
        Settings()
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
            color = DarkBlue
        )
    }
}

@Composable
fun Settings() {

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
            color = DarkBlue,
            modifier = Modifier.padding(start = 10.dp, bottom = 2.dp)
        )

        SettingCard(title = stringResource(R.string.show_timer), onCheckedChange = {})
        SettingCard(title = stringResource(R.string.show_score), onCheckedChange = {})
        SettingCard(
            title = stringResource(R.string.hints),
            description = stringResource(R.string.hints_setting_desc),
            onCheckedChange = {}
        )

        Divider(
            color = DarkBlue,
            thickness = 1.dp,
            modifier = Modifier
                .width(dividerWidth.value)
                .padding(top = 10.dp, bottom = 10.dp)
        )

        Text(
            text = stringResource(R.string.theme),
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = DarkBlue,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp, bottom = 2.dp)
        )

        SettingCard(
            title = stringResource(R.string.dark_theme),
            initialState = false,
            onCheckedChange = {
                darkModeOn()
            })
        //ThemeCard()
    }
}

@Composable
fun SettingCard(
    title: String,
    description: String = "",
    initialState: Boolean = true,
    onCheckedChange: ((Boolean)) -> Unit
) {
    Card(
        //border = BorderStroke(width = 0.5.dp, color = DarkBlue),
        modifier = Modifier.height(60.dp),
        elevation = 0.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            Column() {
                Text(text = title, fontSize = 15.sp)
                Text(text = description, fontSize = 12.sp, color = Color.Gray)
            }

            var checkedState = remember { mutableStateOf(initialState) }
            Switch(
                checked = checkedState.value,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(NormalBlue)
            )
        }
    }
}

@Composable
fun ThemeCard() {
    Card(
        border = BorderStroke(width = 0.5.dp, color = DarkBlue),
        modifier = Modifier.height(50.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = "Theme", fontSize = 18.sp)

            var expanded by remember { mutableStateOf(false) }
            DropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Blue", fontSize = 15.sp)
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Red", fontSize = 15.sp)
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Black", fontSize = 15.sp)
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Orange", fontSize = 15.sp)
                }
            }

            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "icon",
                    tint = NormalBlue,
                    modifier = Modifier.size(40.dp)
                )
            }

        }
    }
}


