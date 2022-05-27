package mp.sudoku.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


object ScreenRouter {
    var currentScreen: MutableState<Int> = mutableStateOf(1)
    val HOMESCREEN = 1
    val DIFFICULTYSCREEN = 2
    val SETTINGSCREEN = 3
    val STATSSCREEN = 4
    val RULESSCREEN = 5


    fun navigateTo(destination: Int) {
        currentScreen.value = destination
    }


}


@Composable
fun MainScreen() {
    when (ScreenRouter.currentScreen.value) {
        1 -> HomeLayout()
        2 -> DifficultyLayout()
        3 -> SettingsLayout()
        4 -> StatsLayout()
        5 -> RulesLayout()
    }
}

