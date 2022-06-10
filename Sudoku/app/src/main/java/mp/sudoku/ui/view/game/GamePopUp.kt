package mp.sudoku.ui.view.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import mp.sudoku.R
import mp.sudoku.ui.view.ScreenRouter

@Composable
fun WonGamePopUp() {
    Popup(alignment = Alignment.Center) {
        Card(
            backgroundColor = MaterialTheme.colors.primary,
            border = BorderStroke(2.dp, MaterialTheme.colors.secondary),
            elevation = 5.dp,
            modifier = Modifier
                .height(400.dp)
                .width(250.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, bottom = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val image: Painter = painterResource(id = R.drawable.trophy)
                    Image(painter = image, contentDescription = "")
                }

                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "You Won!", fontSize = 25.sp)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            ScreenRouter.navigateTo(destination = ScreenRouter.HOMESCREEN)
                        },
                        border = BorderStroke(1.dp, MaterialTheme.colors.secondary)
                    ) {
                        Text(text = "Return to Home")
                    }
                }
            }
        }
    }

}

@Composable
fun LostGamePopUp() {
    Popup(alignment = Alignment.Center) {
        Card(
            backgroundColor = MaterialTheme.colors.primary,
            border = BorderStroke(2.dp, MaterialTheme.colors.secondary),
            elevation = 5.dp,
            modifier = Modifier
                .height(400.dp)
                .width(300.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, bottom = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val image: Painter = painterResource(id = R.drawable.gameover)
                    Image(painter = image, contentDescription = "")
                }

                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "You Lost!", fontSize = 25.sp)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            ScreenRouter.navigateTo(destination = ScreenRouter.HOMESCREEN)
                        },
                        border = BorderStroke(1.dp, MaterialTheme.colors.secondary)
                    ) {
                        Text(text = "Return to Home")
                    }

                    Button(
                        onClick = {
                            ScreenRouter.navigateTo(destination = ScreenRouter.previousScreen.value)
                        },
                        border = BorderStroke(1.dp, MaterialTheme.colors.secondary)
                    ) {
                        Text(text = "View Grid")
                    }
                }
            }
        }
    }
}
