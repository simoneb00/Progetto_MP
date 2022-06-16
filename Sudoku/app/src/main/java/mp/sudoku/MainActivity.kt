package mp.sudoku

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import mp.sudoku.model.CurrentGame
import mp.sudoku.model.Game
import mp.sudoku.ui.theme.SudokuTheme
import mp.sudoku.ui.view.MainScreen
import mp.sudoku.ui.view.ScreenRouter
import mp.sudoku.viewmodel.GameVM

class MainActivity : ComponentActivity() {

    var gameToResume = Game()

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SudokuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus && ScreenRouter.currentScreen.value == ScreenRouter.GAMESCREEN) {
            CurrentGame.getInstance().current = gameToResume
            CurrentGame.getInstance().timer.start()
        }

        if (!hasFocus && ScreenRouter.currentScreen.value == ScreenRouter.GAMESCREEN) {
            println("game info:" +
                    CurrentGame.getInstance().current?.grid + '\n' +
                    CurrentGame.getInstance().current?.score + '\n' +
                    CurrentGame.getInstance().current?.noteGrid + '\n' +
                    CurrentGame.getInstance().current?.timer + '\n'
            )

            val gameVM = GameVM(ScreenRouter.application)

            if (CurrentGame.getInstance().current?.grid != null) {

                gameToResume = CurrentGame.getInstance().current!!

                gameVM.updateGame(
                    board = CurrentGame.getInstance().current?.grid!!,
                    noteBoard = CurrentGame.getInstance().current?.noteGrid!!,
                    timer = CurrentGame.getInstance().current?.timer!!,
                    finished = 0,
                    cancel = false
                )
            }

        }
    }
}
