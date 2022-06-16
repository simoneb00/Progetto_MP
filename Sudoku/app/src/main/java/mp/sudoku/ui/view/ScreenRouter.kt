package mp.sudoku.ui.view

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import mp.sudoku.ui.view.game.GameLayout
import mp.sudoku.ui.view.game.TempGameLayout
import mp.sudoku.ui.view.game.WonGamePopUp
import mp.sudoku.ui.view.resume.GameDetailsLayout
import mp.sudoku.ui.view.resume.ResumeLayout


object ScreenRouter {
    val game: MutableState<List<List<String>>> = mutableStateOf(listOf(listOf("")))
    var currentScreen: MutableState<Int> = mutableStateOf(1)
    var previousScreen: MutableState<Int> = mutableStateOf(1)
    var difficulty: MutableState<String> = mutableStateOf("easy")
    lateinit var application: Application


    const val HOMESCREEN = 1
    const val DIFFICULTYSCREEN = 2
    const val SETTINGSCREEN = 3
    const val STATSSCREEN = 4
    const val RULESSCREEN = 5
    const val GAMESCREEN = 6
    const val RESUMESCREEN = 7
    const val GAMEDETAILSSCREEN = 8
    const val WONGAMEPOPUP = 9

    fun navigateTo(
        source: Int = currentScreen.value,
        destination: Int
    ) {
        currentScreen.value = destination
        previousScreen.value = source
    }

}

@Composable
fun GetCurrentContext() {
    ScreenRouter.application = LocalContext.current.applicationContext as Application
}


@Composable
fun MainScreen() {

    GetCurrentContext()

    when (ScreenRouter.currentScreen.value) {
        1 -> HomeLayout()
        2 -> DifficultyLayout()
        3 -> SettingsLayout()
        4 -> StatsLayout()
        5 -> RulesLayout()
        6 -> TempGameLayout(ScreenRouter.difficulty.value)
        7 -> ResumeLayout()
        8 -> GameDetailsLayout()
        9 -> WonGamePopUp()
    }
}

