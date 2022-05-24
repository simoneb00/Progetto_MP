package mp.sudoku.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun UpperBar(backButton: Boolean) {

    if (backButton) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Start) {
                SquareButton(icon = Icons.Filled.ArrowBack, onClick = {ScreenRouter.navigateTo(ScreenRouter.HOMESCREEN)})
            }

            Row(horizontalArrangement = Arrangement.End) {
                SquareButton(icon = Icons.Rounded.Settings, onClick = {})    // TODO
                SquareButton(icon = Icons.Rounded.Info, onClick = {})   // TODO
            }
        }
    } else {

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .padding(top = 10.dp, end = 10.dp)
        ) {
            SquareButton(icon = Icons.Rounded.Settings, onClick = {})
            SquareButton(icon = Icons.Rounded.Info, onClick = {})
        }
    }
}