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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
import mp.sudoku.ui.view.ScreenRouter
import mp.sudoku.ui.view.components.TopBar
import mp.sudoku.viewmodel.*
import java.util.*


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GameLayout(
    difficulty: String,
    gameGrid: List<List<String>> = listOf(listOf("")),
    resume: Boolean = false
) {

    val settingsVM = SettingsVM(LocalContext.current.applicationContext)
    val activeGameVM = ActiveGameVM()
    val resume = rememberSaveable {
        mutableStateOf(resume)
    }

    val allGames by settingsVM.allGames.observeAsState(listOf())

    val gameVM = GameVM(
        LocalContext
            .current.applicationContext as Application
    )

    val s = rememberSaveable {
        mutableStateOf(gameGrid)
    }
    val stopWatch = rememberSaveable {
        mutableStateOf(StopWatch())
    }

    var isCompleted by remember { mutableStateOf(activeGameVM.isCompleted, neverEqualPolicy()) }

    activeGameVM.subCompletedState = {
        isCompleted = it
    }

    val gridState = rememberSaveable {
        mutableStateOf(activeGameVM.gridState, neverEqualPolicy())
    }

    var notes: List<List<Int>> = listOf(
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
        listOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
    )

    activeGameVM.subGridState1 = {
        gridState.value = it
    }


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (s.value != listOf(listOf(""))) {
            TopBar(
                gridState = activeGameVM.gridState, noteState = activeGameVM.notesState,
                stopWatch = stopWatch.value
            )
            GridButtons(difficulty, settingsVM, stopWatch.value, activeGameVM)

            //if (!isCompleted)
            if (CurrentGame.getInstance().getCurrent() != null && CurrentGame.getInstance()
                    .getCurrent()!!.noteGrid != "empty"
            ) {
                notes = Adapter.changeStringToInt(
                    Adapter.boardPersistenceFormatToList(
                        CurrentGame.getInstance().getCurrent()!!.noteGrid
                    )
                )
            }
            Grid(
                values = Adapter.changeStringToInt(s.value),
                notes = notes,
                activeGameVM = activeGameVM,
                resume = resume.value
            )
            /*else {
                println("**************************************************************")
                println(Adapter.intListToStringList(Adapter.hashMapToList(gridState.value)))
                println("**************************************************************")
                println(s.value)
                s.value = Adapter.intListToStringList(Adapter.hashMapToList(gridState.value))
                Grid(values = Adapter.hashMapToList(gridState.value), activeGameVM = activeGameVM)
            }

             */

            if (resume.value) {
                stopWatch.value = CurrentGame.getInstance().timer
            } else {
                gameVM.addGame(board = s.value, difficulty = difficulty, id = allGames.size + 1)
                resume.value = true
            }
        } else {
            CircularProgressIndicator(
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(top = 8.dp)
            )
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

        if (isCompleted) {

            s.value = Adapter.intListToStringList(Adapter.hashMapToList(gridState.value))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        if (activeGameVM.checkGrid(gridState.value)) {

                            println(
                                "current board: " + CurrentGame.getInstance().getCurrent()!!.grid
                            )
                            println(
                                "current timer: " + CurrentGame.getInstance().getCurrent()!!.timer
                            )
                            println(
                                "current score: " + CurrentGame.getInstance().getCurrent()!!.score
                            )

                            ScreenRouter.navigateTo(destination = ScreenRouter.WONGAMEPOPUP)
                            gameVM.updateGame(
                                board = CurrentGame.getInstance().getCurrent()!!.grid,
                                noteBoard = CurrentGame.getInstance().getCurrent()!!.grid,
                                timer = CurrentGame.getInstance().getCurrent()!!.timer,
                                finished = 1
                            )
                        } else {
                            /*
                            println(Adapter.hashMapToList(gridState.value))
                            println(
                                "adapted string: " + Adapter.intListToStringList(
                                    Adapter.hashMapToList(
                                        gridState.value
                                    )
                                )
                            )
                            s.value =
                                Adapter.intListToStringList(Adapter.hashMapToList(gridState.value))

                             */
                        }

                        isCompleted = false
                    },
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Check", fontSize = 20.sp)
                }
            }
        }
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
                Text(text = stringResource(R.string.cancel))
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
                Text(text = stringResource(R.string.notes))
            }
        }

        if (settingsVM.getHintsSetting()) {
            IconButton(onClick = {
                activeGameVM.incrementCounter()
                activeGameVM.getHint()
            }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.Lightbulb,
                        contentDescription = "",
                        tint = MaterialTheme.colors.secondary
                    )
                    Text(text = stringResource(R.string.hint))
                }
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun NumberButtons(
    activeGameVM: ActiveGameVM
) {

    val numbers = rememberSaveable {
        mutableStateOf(activeGameVM.buttonsNumbers)
    }

    activeGameVM.subButtonsNumbers = { numbers.value = it }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 1..9) {

            if (numbers.value.contains(i)) {
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
            } else {
                Spacer(modifier = Modifier.size(height = 60.dp, width = 35.dp))
            }
        }
    }
}

@Composable
fun GridButtons(
    difficulty: String, settingsVM: SettingsVM, stopwatch: StopWatch,
    activeGameVM: ActiveGameVM
) {

    stopwatch.start()

    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(top = 10.dp, bottom = 5.dp)
    ) {
        val screenWidth = with(LocalDensity.current) {
            constraints.maxWidth.toDp()
        }

        val margin = 15

        Row(
            modifier = Modifier.width(screenWidth - margin.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = stringResource(R.string.difficulty) + ": " + difficulty)
            if (settingsVM.getTimerSetting())
                Text(text = "Timer: " + stopwatch.formattedTime)
            if (settingsVM.getScoreSetting())
                Text(text = stringResource(R.string.score) + ": " + activeGameVM.getScore())
        }
    }
}


