package mp.sudoku.ui.view.game

import androidx.compose.runtime.Composable
import mp.sudoku.ui.view.ScreenRouter

@Composable
fun TempGameLayout(difficulty:String) {
    if(ScreenRouter.previousScreen.value == ScreenRouter.DIFFICULTYSCREEN){
        GameLayout(difficulty = difficulty,isResume = false)
    }
    else{
        GameLayout(difficulty = difficulty,ScreenRouter.game.value, isResume = true)
    }
}