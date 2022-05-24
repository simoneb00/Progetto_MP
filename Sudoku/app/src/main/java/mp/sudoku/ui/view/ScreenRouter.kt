package mp.sudoku.ui.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


object ScreenRouter {
    var currentScreen: MutableState<Int> = mutableStateOf(1)
    val HOMESCREEN = 1
    val DIFFICULTYSCREEN = 2


    fun navigateTo(destination: Int) {
        currentScreen.value = destination
    }


}




@Composable
fun MainScreen() {
    when (ScreenRouter.currentScreen.value) {
        1 -> HomeLayout()
        2 -> DifficultyLayout()
    }
}

