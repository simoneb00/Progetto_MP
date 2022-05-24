package mp.sudoku.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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
        UpperBar(false)
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
        NavigationButton(text = "New Game", value = ScreenRouter.DIFFICULTYSCREEN)
        NavigationButton(text = "Resume", value = ScreenRouter.HOMESCREEN)
        NavigationButton(text = "Statistics", value = ScreenRouter.HOMESCREEN)
        NavigationButton(text = "Settings", value = ScreenRouter.HOMESCREEN)
    }
}


