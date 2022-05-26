package mp.sudoku.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout
import mp.sudoku.ui.theme.NormalBlue

@Composable
fun TopBar(
    includeBackButton: Boolean,
    includeSettingsButton: Boolean,
    includeGuideButton: Boolean,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White
) {

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .background(color = backgroundColor)
    ) {
        val (backButton, settingsButton, guideButton) = createRefs()

        if (includeBackButton) {
            IconButton(onClick = { ScreenRouter.navigateTo(ScreenRouter.HOMESCREEN) },
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(backButton) {
                        start.linkTo(parent.start, margin = 5.dp)
                    }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Icon", tint = NormalBlue)
            }
        }

        if (includeGuideButton) {
            IconButton(onClick = { /* TODO */ }, modifier = Modifier
                .size(30.dp)
                .constrainAs(guideButton) {
                    end.linkTo(parent.end, margin = 5.dp)
                }) {
                Icon(Icons.Rounded.Info, contentDescription = "Icon", tint = NormalBlue)
            }
        }

        if (includeSettingsButton) {
            IconButton(onClick = { ScreenRouter.navigateTo(ScreenRouter.SETTINGSCREEN) },
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(settingsButton) {
                        end.linkTo(guideButton.start, margin = 5.dp)
                    }) {
                Icon(Icons.Rounded.Settings, contentDescription = "Icon", tint = NormalBlue)
            }
        }
    }
}

