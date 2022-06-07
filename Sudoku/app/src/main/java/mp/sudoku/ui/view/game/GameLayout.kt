package mp.sudoku.ui.view.game

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardBackspace
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mp.sudoku.R
import mp.sudoku.model.CurrentGame
import mp.sudoku.model.volley.VolleyGrid
import mp.sudoku.ui.view.components.TopBar
import mp.sudoku.viewmodel.*
import java.util.*


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GameLayout(difficulty: String) {
    val settingsVM = SettingsVM(LocalContext.current.applicationContext)
    val activeGameVM = ActiveGameVM()
    var isCompleted by remember { mutableStateOf(activeGameVM.isCompleted, neverEqualPolicy()) }
    var resume = false

    val gameVM = GameVM(
        LocalContext
            .current.applicationContext as Application
    )

    val s = rememberSaveable {
        mutableStateOf(listOf(listOf("")))
    }
    if(CurrentGame.getInstance().getOnlyCurrent() != null){
        s.value = gameVM.resumeGame()
        resume = true
    }

    val stopwatch = rememberSaveable {
        mutableStateOf(StopWatch())
    }

    activeGameVM.subCompletedState = {
        isCompleted = it
    }


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TopBar(activeGameVM = activeGameVM, stopWatch = stopwatch.value)
        if(s.value != listOf(listOf(""))){
            GridButtons(difficulty,settingsVM,stopwatch,activeGameVM)
            Grid(values = Adapter.changeStringToInt(s.value), activeGameVM = activeGameVM)
            if(!resume)
                gameVM.addGame(board = s.value, difficulty = difficulty)
        }
        else{
            CircularProgressIndicator(color = MaterialTheme.colors.secondary)
            gameVM.loadData(
                difficulty.lowercase(Locale.getDefault())
            ) {
                val sType = object : TypeToken<VolleyGrid>() {}.type
                val gson = Gson()
                val mData = gson.fromJson<VolleyGrid>(it, sType)
                s.value = mData.board
            }
        }
        GameButtons(activeGameVM, settingsVM)
        NumberButtons(activeGameVM)

       // if (isCompleted) {
            CheckButton(activeGameVM)
        //}
    }
}

@Composable
fun GameButtons(
    activeGameVM: ActiveGameVM,
    settingsVM: SettingsVM
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

        if (settingsVM.getHintsSetting()) {
            IconButton(onClick = {
                activeGameVM.incrementCounter()
                activeGameVM.updateGrid(activeGameVM.getHint())
            }) {
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
fun GridButtons(
    difficulty: String, settingsVM: SettingsVM, stopwatch: MutableState<StopWatch>,
    activeGameVM: ActiveGameVM) {

    if(stopwatch.value.formattedTime == "")
        stopwatch.value.start()


    Row(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 30.dp, bottom = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(R.string.difficulty) +  ": " + difficulty)
        if (settingsVM.getTimerSetting())
            Text(text = "Timer: " + stopwatch.value.formattedTime)
        if (settingsVM.getScoreSetting())
        Text(text = stringResource(R.string.score) + ": " + activeGameVM.getScore())
    }
}

@Composable
fun CheckButton(activeGameVM: ActiveGameVM) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                      activeGameVM.checkGrid()
            },
            border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Check", fontSize = 20.sp)
        }
    }
}