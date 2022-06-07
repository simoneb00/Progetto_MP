package mp.sudoku.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import mp.sudoku.model.Game
import mp.sudoku.ui.view.ScreenRouter.game
import mp.sudoku.ui.view.game.GameLayout
import mp.sudoku.ui.view.resume.GameDetailsLayout


object ScreenRouter {
    var currentScreen: MutableState<Int> = mutableStateOf(1)
    var previousScreen: MutableState<Int> = mutableStateOf(1)
    var difficulty: MutableState<String> = mutableStateOf("easy")
    var game: Game = Game()

    const val HOMESCREEN = 1
    const val DIFFICULTYSCREEN = 2
    const val SETTINGSCREEN = 3
    const val STATSSCREEN = 4
    const val RULESSCREEN = 5
    const val GAMESCREEN = 6
    const val RESUMESCREEN = 7
    const val GAMEDETAILSSCREEN = 8


    fun navigateTo(
        source: Int = currentScreen.value,
        destination: Int
    ) {
        currentScreen.value = destination
        previousScreen.value = source
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
        6 -> GameLayout(ScreenRouter.difficulty.value)
        7 -> ResumeLayout()
        8 -> GameDetailsLayout(game = game)
    }
}

