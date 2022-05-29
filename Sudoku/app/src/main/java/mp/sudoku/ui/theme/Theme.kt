package mp.sudoku.ui.theme

import android.hardware.lights.Light
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import java.util.Collections.copy

/* TODO */
private var DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Color.Black
)

private var LightColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = BackgroundWhite,
    secondary = Blue900,
    secondaryVariant = Blue500,
    background = Color.White,
    surface = Color.White,
    onPrimary = Black,
    onSecondary = Black,
    onSurface = Black,
    onBackground = Black
)

private var colors: MutableState<Colors> = mutableStateOf(LightColorPalette)

@Composable
fun SudokuTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    MaterialTheme(
        colors = colors.value,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

fun invertTheme() {
    colors.value = if (colors.value == LightColorPalette) DarkColorPalette else LightColorPalette
}

fun isDarkModeOn(): Boolean {
    return colors.value == DarkColorPalette
}