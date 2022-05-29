package mp.sudoku.ui.theme

import android.hardware.lights.Light
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.Black
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.White

    /* Other default colors to override

    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

var colors: MutableState<Colors> = mutableStateOf(LightColorPalette)

@Composable
fun SudokuTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {


    MaterialTheme(
        colors = colors.value,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

fun darkModeOn() {
    colors.value = DarkColorPalette
}