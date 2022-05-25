package mp.sudoku.ui.view

import android.provider.Settings.System.getString
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mp.sudoku.R

@Preview(showBackground = true)
@Composable
fun HomeLayout() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        TopBar(includeBackButton = false, includeGuideButton = true, includeSettingsButton = true ,modifier = Modifier)
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
            .height(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationButton(text = stringResource(R.string.new_game), destination = ScreenRouter.DIFFICULTYSCREEN)
        NavigationButton(text = stringResource(R.string.resume), destination = ScreenRouter.HOMESCREEN)
        NavigationButton(text = stringResource(R.string.statistics), destination = ScreenRouter.HOMESCREEN)
        NavigationButton(text = stringResource(R.string.settings), destination = ScreenRouter.SETTINGSCREEN)
    }
}


