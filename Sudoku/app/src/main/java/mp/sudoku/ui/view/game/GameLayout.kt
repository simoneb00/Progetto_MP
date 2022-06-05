package mp.sudoku.ui.view.game

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardBackspace
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.ui.view.TopBar
import mp.sudoku.viewmodel.ActiveGameVM

@Preview(showBackground = true)
@Composable
fun GameLayout() {

    val activeGameVM = ActiveGameVM()
    var isCompleted by remember { mutableStateOf(activeGameVM.isCompleted, neverEqualPolicy()) }

    activeGameVM.subCompletedState = {
        isCompleted = it
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TopBar()
        GridButtons()
        Grid(activeGameVM = activeGameVM)
        GameButtons(activeGameVM)
        NumberButtons(activeGameVM)

        if (isCompleted) {
            CheckButton()
        }
    }
}

@Composable
fun GameButtons(
    activeGameVM: ActiveGameVM
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp, end = 50.dp, top = 20.dp, bottom = 0.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {
            activeGameVM.cancelCell()
        }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Rounded.KeyboardBackspace,
                    contentDescription = "",
                    tint = MaterialTheme.colors.secondary
                )
                Text(text = "Cancel")
            }
        }

        var isRed by remember {
            mutableStateOf(false)
        }

        IconButton(onClick = {
            activeGameVM.notesMode = !activeGameVM.notesMode
            isRed = !isRed
        }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "",
                    tint = if (isRed) {
                        Color.Red
                    } else MaterialTheme.colors.secondary
                )
                Text(text = "Notes")
            }
        }

        IconButton(onClick = { /*TODO*/ }) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.Lightbulb,
                    contentDescription = "",
                    tint = MaterialTheme.colors.secondary
                )
                Text(text = "Hint")
            }
        }
    }
}

@Composable
fun NumberButtons(
    activeGameVM: ActiveGameVM
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 1..9) {
            Button(
                onClick = {
                    activeGameVM.updateGrid(value = i)
                },
                modifier = Modifier.size(height = 60.dp, width = 35.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(
                    text = i.toString(),
                    color = MaterialTheme.colors.secondary,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun GridButtons() {
    Row(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 30.dp, bottom = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Difficulty:")  // TODO linkare vm
        Text(text = "Timer: ")      // TODO linkare vm
        Text(text = "Score: ")      // TODO linkare vm

    }
}

@Composable
fun CheckButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { /*TODO*/ },
            border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Check", fontSize = 20.sp)
        }
    }
}