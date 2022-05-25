package mp.sudoku.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.ui.theme.NormalBlue

@Composable
fun NavigationButton(text: String?, destination: Int) {
    Button(
        onClick = { ScreenRouter.navigateTo(destination) },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        modifier = Modifier.size(height = 50.dp, width = 250.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 2.dp, color = NormalBlue)
    ) {
        Text(
            text = text,
            color = NormalBlue,
            fontSize = 20.sp
        )
    }
}


@Composable
fun SquareButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(onClick = onClick, modifier = Modifier.size(30.dp)) {
        Icon(icon, contentDescription = "Icon", tint = NormalBlue)
    }
}
