package mp.sudoku.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import mp.sudoku.R
import mp.sudoku.viewmodel.SettingsVM

private var DarkColorPalette = darkColors(
    primary = Black,
    secondary = Color.White,
    background = Black,
    surface = Color(0xFF121212),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onSurface = Color.White,
    onBackground = Color.White
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
var logoId = mutableStateOf(R.drawable.sudoku_logo)

@Composable
fun SudokuTheme(content: @Composable () -> Unit) {

    val settingsVM = SettingsVM(LocalContext.current.applicationContext)
    if (settingsVM.getDarkModeSetting()) {
        colors.value = DarkColorPalette
        logoId.value = R.drawable.sudoku_logo_dark
    }

    MaterialTheme(
        colors = colors.value,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

fun invertTheme() {
    colors.value = if (colors.value == LightColorPalette) DarkColorPalette else LightColorPalette
    invertLogoTheme()
}

fun isDarkModeOn(): Boolean {
    return colors.value == DarkColorPalette
}

fun invertLogoTheme() {
    logoId.value = if (logoId.value == R.drawable.sudoku_logo)  R.drawable.sudoku_logo_dark else R.drawable.sudoku_logo
}