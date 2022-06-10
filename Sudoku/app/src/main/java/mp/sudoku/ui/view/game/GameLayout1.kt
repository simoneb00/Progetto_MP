package mp.sudoku.ui.view.game

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import mp.sudoku.model.SudokuCell
import mp.sudoku.model.volley.VolleyGrid
import mp.sudoku.ui.view.ScreenRouter
import mp.sudoku.ui.view.components.TopBar
import mp.sudoku.viewmodel.*
import java.util.*
import kotlin.collections.HashMap

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GameLayout1(difficulty: String) {

    val settingsVM = SettingsVM(LocalContext.current.applicationContext)
    val activeGameVM = ActiveGameVM()
    var isCompleted by remember { mutableStateOf(activeGameVM.isCompleted, neverEqualPolicy()) }
    var isCorrect by remember { mutableStateOf(activeGameVM.isGridCorrect, neverEqualPolicy()) }
    var resume = false

    val allGames by settingsVM.allGames.observeAsState(listOf())

    val gameVM = GameVM(
        LocalContext
            .current.applicationContext as Application
    )

    val s = rememberSaveable {
        mutableStateOf(listOf(listOf("")))
    }
    val stopWatch = rememberSaveable {
        mutableStateOf(StopWatch())
    }

    if (CurrentGame.getInstance().getOnlyCurrent() != null) {
        s.value = gameVM.resumeGame()
        resume = true
    }

    activeGameVM.subCompletedState = {
        isCompleted = it
    }

    activeGameVM.subCorrectState = {
        isCorrect = it
    }

    val gridState = rememberSaveable {
        mutableStateOf(activeGameVM.gridState, neverEqualPolicy())
    }

    activeGameVM.subGridState = {
        gridState.value = it
    }


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (s.value != listOf(listOf(""))) {
            TopBar(activeGameVM = activeGameVM, stopWatch = stopWatch.value)
            GridButtons(difficulty, settingsVM, stopWatch.value, activeGameVM)
            Grid(values = Adapter.changeStringToInt(s.value), activeGameVM = activeGameVM, gridState = gridState)
            if (resume) {
                stopWatch.value = CurrentGame.getInstance().timer
            } else {
                gameVM.addGame(board = s.value, difficulty = difficulty, id = allGames.size + 1)
            }
        } else {
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

        if (isCompleted) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        println(s.value)

                        if (activeGameVM.checkGrid()) {
                            ScreenRouter.navigateTo(destination = ScreenRouter.WONGAMEPOPUP)
                            gameVM.updateGame(
                                board = CurrentGame.getInstance().getCurrent()!!.grid,
                                noteBoard = "",
                                timer = CurrentGame.getInstance().getCurrent()!!.timer,
                                finished = 1,
                                deleteCurrent = false
                            )
                        } else {
                            gameVM.updateGame(
                                board = CurrentGame.getInstance().getCurrent()!!.grid,
                                noteBoard = "",
                                timer = CurrentGame.getInstance().getCurrent()!!.timer,
                                deleteCurrent = false
                            )
                            ScreenRouter.navigateTo(destination = ScreenRouter.LOSTGAMEPOPUP)
                        }

                        println("Observed Grid: ${Adapter.hashMapToList(gridState.value)}")
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
                activeGameVM.getHint()
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

@SuppressLint("MutableCollectionMutableState")
@Composable
fun Grid(
    values: List<List<Int>>,
    activeGameVM: ActiveGameVM,
    isReadOnly: Boolean = false,
    gridState: MutableState<HashMap<Int, SudokuCell>>
) {

    activeGameVM.initGrid(values, isReadOnly)

    BoxWithConstraints {
        val screenWidth = with(LocalDensity.current) {
            constraints.maxWidth.toDp()
        }

        val margin = 15

        Box(
            modifier = Modifier
                .border(width = 2.dp, color = MaterialTheme.colors.secondary)
                .size(screenWidth - margin.dp)
        ) {
            val offset = (screenWidth - margin.dp).value / 9
            SudokuTextFields(offset, activeGameVM, gridState = gridState.value)
            BoardGrid(offset)
        }
    }
}

@Composable
fun BoardGrid(offset: Float) {

    (1 until 9).forEach {
        val width = if (it % 3 == 0) 3.dp else 1.dp
        Divider(
            modifier = Modifier
                .absoluteOffset((offset * it).dp, 0.dp)
                .fillMaxHeight()
                .width(width)
        )

        val height = if (it % 3 == 0) 3.dp else 1.dp
        Divider(
            modifier = Modifier
                .absoluteOffset(0.dp, (offset * it).dp)
                .fillMaxWidth()
                .height(height)
        )
    }
}

@Composable
fun SudokuTextFields(offset: Float, vm: ActiveGameVM, gridState: HashMap<Int, SudokuCell>) {


    gridState.values.forEach { cell ->
        var text = cell.value.toString()
        var note = cell.note.toString()

        if (text == "0") text = ""
        if (note == "0") note = ""

        Box(
            modifier = Modifier
                .absoluteOffset(
                    (offset * (cell.x)).dp,
                    (offset * (cell.y)).dp
                )
                .width(offset.dp)
                .height(offset.dp)
                .background(
                    when {
                        cell.isSelected -> MaterialTheme.colors.secondaryVariant
                        cell.isInEvidence -> MaterialTheme.colors.primaryVariant
                        else -> MaterialTheme.colors.surface
                    }
                )
                .clickable {
                    //if (!cell.isReadOnly)
                    vm.selectCell(cell.x, cell.y)
                },
            contentAlignment = if (note == "") Alignment.Center else Alignment.TopStart
        ) {
            if (note == "") {
                Text(
                    text = text,
                    color = if (cell.color == "Red") Color.Red else MaterialTheme.colors.onPrimary
                )
            } else {
                Text(
                    text = note,
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                    fontSize = 10.sp

                )
            }
        }
    }
}