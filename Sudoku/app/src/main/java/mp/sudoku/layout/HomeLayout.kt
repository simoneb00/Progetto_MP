package mp.sudoku.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.R
import mp.sudoku.ui.theme.NormalBlue

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

    val onClick = {}    // TODO

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp)
            .height(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationButton(text = "New Game", onClick = onClick)
        NavigationButton(text = "Resume", onClick = onClick)
        NavigationButton(text = "Statistics", onClick = onClick)
        NavigationButton(text = "Settings", onClick = onClick)
    }
}

