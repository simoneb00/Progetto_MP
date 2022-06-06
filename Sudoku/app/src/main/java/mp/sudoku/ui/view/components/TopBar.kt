package mp.sudoku.ui.view.components

import android.app.Application
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
import mp.sudoku.ui.view.ScreenRouter
import mp.sudoku.viewmodel.GameVM

@Composable
fun TopBar(
    includeBackButton: Boolean = true,
    includeSettingsButton: Boolean = true,
    includeGuideButton: Boolean = true,
    backgroundColor: Color = MaterialTheme.colors.background
) {
    val gameVM = GameVM(
        LocalContext
            .current.applicationContext as Application
    )



    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .background(color = backgroundColor)
    ) {
        val (backButton, settingsButton, guideButton) = createRefs()

        if (includeBackButton) {
            IconButton(onClick = {
                if (
                    ScreenRouter.currentScreen.value == ScreenRouter.DIFFICULTYSCREEN ||
                    ScreenRouter.currentScreen.value == ScreenRouter.RESUMESCREEN
                ) {
                    ScreenRouter.navigateTo(destination = ScreenRouter.HOMESCREEN)
                } else if (ScreenRouter.currentScreen.value == ScreenRouter.GAMESCREEN) {
                    ScreenRouter.navigateTo(destination = ScreenRouter.DIFFICULTYSCREEN)
                } else {
                    ScreenRouter.navigateTo(
                        destination = ScreenRouter.previousScreen.value,
                        source = ScreenRouter.currentScreen.value
                    )
                }
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
            IconButton(onClick = { ScreenRouter.navigateTo(destination = ScreenRouter.RULESSCREEN) },
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
            IconButton(onClick = { ScreenRouter.navigateTo(destination = ScreenRouter.SETTINGSCREEN) },
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

