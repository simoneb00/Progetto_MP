package mp.sudoku.ui.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun DifficultyLayout() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        TopBar(includeBackButton = true, includeSettingsButton = true, includeGuideButton = true)
        DifficultyText()
        DifficultyButtons()
    }
}

@Composable
fun DifficultyText() {
    Row(modifier = Modifier.fillMaxWidth().padding(top = 100.dp), horizontalArrangement = Arrangement.Center) {
        Text(text = "Select Difficulty", fontSize = 30.sp, fontWeight = FontWeight.SemiBold ,color = MaterialTheme.colors.secondary)
    }
}

@Composable
fun DifficultyButtons() {

    /* buttons animation */
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val height = animateDpAsState(
        targetValue = if (animationPlayed) 210.dp else 0.dp,
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
            .padding(top = 125.dp)
            .height(height.value),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationButton(text = "Easy", destination = ScreenRouter.GAMELAYOUT)
        NavigationButton(text = "Normal", destination = ScreenRouter.GAMELAYOUT)
        NavigationButton(text = "Hard", destination = ScreenRouter.GAMELAYOUT)
    }
}