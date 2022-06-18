package mp.sudoku.ui.view.components

import android.app.Activity
import android.app.Application
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import mp.sudoku.model.CurrentGame
import mp.sudoku.model.SudokuCell
import mp.sudoku.ui.view.ScreenRouter
import mp.sudoku.viewmodel.Adapter
import mp.sudoku.viewmodel.GameVM
import mp.sudoku.viewmodel.StopWatch

@Composable
fun TopBar(
    includeBackButton: Boolean = true,
    includeSettingsButton: Boolean = true,
    includeGuideButton: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.background,
    stopWatch: StopWatch,
    noteState: MutableList<MutableList<Int>> = mutableListOf(mutableListOf()),
    gridState: HashMap<Int, SudokuCell> = HashMap()
) {

    val activity = LocalContext.current as Activity?



    /* this handles the device's back button, covering each possible case */
    BackHandler {
        backButtonHandler(activity, gridState, noteState, stopWatch)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .background(color = backgroundColor)
    ) {
        val (backButton, settingsButton, guideButton) = createRefs()

        if (includeBackButton) {

            /* Back Button */
            IconButton(onClick = {
                backButtonHandler(activity, gridState, noteState, stopWatch)
            },
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(backButton) {
                        start.linkTo(parent.start, margin = 5.dp)
                    }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Icon",
                    tint = MaterialTheme.colors.secondary
                )
            }
        }

        if (includeGuideButton) {
            /* Guide Button */
            IconButton(onClick = {
                if (ScreenRouter.currentScreen.value == ScreenRouter.GAMESCREEN) {
                    /* we're opening rules screen while playing a game */
                    val gameVM = GameVM(ScreenRouter.application)

                    /* the timer needs to be paused */
                    CurrentGame.getInstance().timer.pause()

                    /* the game needs to be saved, because it will have to be restored when returning back to the game screen */
                    gameVM.updateGame(
                        board = CurrentGame.getInstance().current?.grid!!,
                        noteBoard = CurrentGame.getInstance().current?.noteGrid!!,
                        timer = CurrentGame.getInstance().current?.timer!!,
                        finished = 0,
                        cancel = false
                    )
                }
                ScreenRouter.navigateTo(destination = ScreenRouter.RULESSCREEN)
            },
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(guideButton) {
                        end.linkTo(parent.end, margin = 5.dp)
                    }) {
                Icon(
                    Icons.Rounded.Info,
                    contentDescription = "Icon",
                    tint = MaterialTheme.colors.secondary
                )
            }
        }

        if (includeSettingsButton) {
            /* Settings Button */
            IconButton(onClick = {
                if (ScreenRouter.currentScreen.value == ScreenRouter.GAMESCREEN) {
                    /* we're opening settings while playing a game */
                    val gameVM = GameVM(ScreenRouter.application)

                    /* the timer needs to be paused */
                    CurrentGame.getInstance().timer.pause()

                    /* the game needs to be saved, because it will have to be restored when returning back to the game screen */
                    gameVM.updateGame(
                        board = CurrentGame.getInstance().current?.grid!!,
                        noteBoard = CurrentGame.getInstance().current?.noteGrid!!,
                        timer = CurrentGame.getInstance().current?.timer!!,
                        finished = 0,
                        cancel = false
                    )
                }
                ScreenRouter.navigateTo(destination = ScreenRouter.SETTINGSCREEN)
            },
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(settingsButton) {
                        end.linkTo(guideButton.start, margin = 5.dp)
                    }) {
                Icon(
                    Icons.Rounded.Settings,
                    contentDescription = "Icon",
                    tint = MaterialTheme.colors.secondary
                )
            }
        }
    }
}


fun backButtonHandler(
    activity: Activity?,
    gridState: HashMap<Int, SudokuCell>,
    noteState: MutableList<MutableList<Int>>,
    stopWatch: StopWatch
) {
    when (ScreenRouter.currentScreen.value) {

        ScreenRouter.HOMESCREEN -> activity?.finish()   // if user is on the home screen and presses the back button, the application is closed
        ScreenRouter.DIFFICULTYSCREEN -> ScreenRouter.navigateTo(destination = ScreenRouter.HOMESCREEN)
        ScreenRouter.RESUMESCREEN -> ScreenRouter.navigateTo(destination = ScreenRouter.HOMESCREEN)

        /* the following saves the current game in persistence, when the user presses the back button  */
        ScreenRouter.GAMESCREEN -> updateGame(
            activity!!.applicationContext as Application,
            gridState,
            noteState,
            stopWatch
        )

        ScreenRouter.GAMEDETAILSSCREEN -> ScreenRouter.navigateTo(destination = ScreenRouter.RESUMESCREEN)

        ScreenRouter.SETTINGSCREEN -> {
            if (ScreenRouter.previousScreen.value == ScreenRouter.GAMESCREEN) {
                /* if the user presses the back button while in settings screen and he was previously playing a game, the game needs to be resumed */
                ScreenRouter.game.value = Adapter.boardPersistenceFormatToList(CurrentGame.getInstance().current!!.grid)
            }
            ScreenRouter.navigateTo(destination = ScreenRouter.previousScreen.value)
        }
        ScreenRouter.RULESSCREEN -> {
            if (ScreenRouter.previousScreen.value == ScreenRouter.GAMESCREEN) {
                /* if the user presses the back button while in rules screen and he was previously playing a game, the game needs to be resumed */
                ScreenRouter.game.value = Adapter.boardPersistenceFormatToList(CurrentGame.getInstance().current!!.grid)
            }
            ScreenRouter.navigateTo(destination = ScreenRouter.previousScreen.value)
        }
        else -> {
            ScreenRouter.navigateTo(
                destination = ScreenRouter.previousScreen.value,
                source = ScreenRouter.currentScreen.value
            )
        }
    }
}


fun updateGame(
    application: Application, gridState: HashMap<Int, SudokuCell>,
    noteState: MutableList<MutableList<Int>>, stopWatch: StopWatch
) {
    try {
        val game = GameVM(application)
        val thisBoard =
            Adapter.boardListToPersistenceFormat(Adapter.hashMapToList(gridState))


        ScreenRouter.game.value = listOf(listOf(""))

        game.updateGame(
            board = thisBoard,
            noteBoard = Adapter.boardListToPersistenceFormat(noteState),
            timer = stopWatch.formattedTime
        )

    } catch (e: Exception) {
        e.printStackTrace()
    }


    ScreenRouter.navigateTo(destination = ScreenRouter.HOMESCREEN)
}


