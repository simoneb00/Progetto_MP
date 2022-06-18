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
import mp.sudoku.model.volley.VolleyGrid
import mp.sudoku.ui.view.ScreenRouter
import mp.sudoku.ui.view.components.TopBar
import mp.sudoku.viewmodel.*
import java.util.*

@SuppressLint("CoroutineCreationDuringComposition", "MutableCollectionMutableState")
@Composable
fun GameLayout(
    difficulty: String,
    gameGrid: List<List<String>> = listOf(listOf("")),
    isResume: Boolean = false
) {

    val settingsVM = SettingsVM(LocalContext.current.applicationContext)
    val activeGameVM = ActiveGameVM()

    val resume = rememberSaveable { mutableStateOf(isResume) }      // true if the game has been resumed

    val allGames by settingsVM.allGames.observeAsState(listOf())    // list of every started game

    val gameVM = GameVM(
        LocalContext
            .current.applicationContext as Application
    )

    /* this variable observes the grid's variations */
    val s = rememberSaveable {
        mutableStateOf(gameGrid)
    }

    val stopWatch = rememberSaveable {
        mutableStateOf(StopWatch())
    }


    /*
    *   this variable observes activeGameVM's variable isCompleted,
    *   in order to know when the grid is full and, therefore, show the check button
    */
    var isCompleted by remember { mutableStateOf(activeGameVM.isCompleted, neverEqualPolicy()) }

    /* this is useful to get changes when activeGameVM updates isCompleted status */
    activeGameVM.subCompletedState = {
        isCompleted = it
    }

    /*
    *   The following variable keeps track of the sudoku grid's state, so the values (or notes) inserted by the user
    *   It is initialized to activeGameVM's gridState, because activeGameVM has the responsibility to create and update gridState
    */
    val gridState = rememberSaveable {
        mutableStateOf(activeGameVM.gridState, neverEqualPolicy())
    }

    /* this is useful to get changes when activeGameVM modifies gridStates */
    activeGameVM.subGridState1 = {
        gridState.value = it
    }

    /*
    *   This variable keeps track of all notes in the grid.
    *   It is initialized at 0 (no notes), in case of a new game
    */
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



    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        if (s.value != listOf(listOf(""))) {
            /*s.value is changed only when Volley updates the value of the new grid, so we enter here only after volley got an answer
            * s "observe" the state of the volley return grid and wait until it is not empty anymore
            */

            /*
            *   TopBar needs the parameters gridState, notesState and stopWatch in order to save the current game in persistence,
            *   if the user clicks on the back button
             */
            TopBar(
                gridState = activeGameVM.gridState,
                noteState = activeGameVM.notesState,
                stopWatch = stopWatch.value
            )

            /* this function creates the row located above the grid, containing difficulty, timer and score values */
            GridButtons(difficulty, settingsVM, stopWatch.value, activeGameVM)

            /* if the game has been resumed, the following updates 'notes' variable */
            if (ActiveGameVM.getCurrent() != null && ActiveGameVM.getCurrentNoteGrid() != "empty") {
                notes = Adapter.changeStringToInt(
                    Adapter.boardPersistenceFormatToList(
                        ActiveGameVM.getCurrentNoteGrid()
                    )
                )
            }

            /*
            *   The following displays the grid, initialized with 's.value' values and notes provided by the variable 'notes'
            *   Moreover, it passes to Grid function the boolean resume, in order to make it know if the game has been resumed or not
            */
            Grid(
                values = Adapter.changeStringToInt(s.value),
                notes = notes,
                activeGameVM = activeGameVM,
                resume = resume.value
            )


            if (resume.value) {
                stopWatch.value = ActiveGameVM.getCurrentTimer()
            } else {
                var maxId = 0
                allGames.forEach { game ->
                    if (game.id > maxId)
                        maxId = game.id
                }

                gameVM.addGame(board = s.value, difficulty = difficulty, id = maxId + 1)
                resume.value = true
            }
        } else {
            /* This else is used to ask Volley for a new http request to get a new grid,
            * so it adds a new Stringrequest in the singleton volley queue
            */
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

        /* the following function creates three buttons (delete, notes, hint) and displays them beneath the grid */
        GameButtons(activeGameVM, settingsVM)

        /* the following function creates and displays the buttons that the user must use to insert numbers in the grid */
        NumberButtons(activeGameVM)

        if (isCompleted) {

            /* We're updating s.value to the completed grid, so when it will be rebuilt, its values will be up to date */
            s.value = Adapter.intListToStringList(Adapter.hashMapToList(gridState.value))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                /* Check button */
                Button(
                    onClick = {
                        if (activeGameVM.checkGrid(gridState.value) || GameVM.validateGrid(Adapter.intListToStringList(Adapter.hashMapToList(gridState.value)))) {
                            /* We enter this if statement only if the grid is correct. There are two possibilities:
                            *   1) The previously computed solution and the contents of the grid coincide
                            *   2) The previously computed solution and the contents of the grid do not coincide, but the grid is correct anyways
                            *
                            *   The second possibility could occur because some generated grids may have more than one solution
                            */
                            ScreenRouter.navigateTo(destination = ScreenRouter.WONGAMEPOPUP)

                            /* the following invocation saves the completed game in persistence */
                            gameVM.updateGame(
                                board = ActiveGameVM.getCurrentGrid(),
                                noteBoard = ActiveGameVM.getCurrentNoteGrid(),
                                timer = stopWatch.value.formattedTime,
                                finished = 1
                            )
                        }

                        /*  Whether the game is correct or not, isCompleted value is put false, so the user can modify the grid
                        *   (this statement only makes sense if the game is incorrect, because we need to make the user able to revise and correct the grid)
                        */
                        isCompleted = false

                    },
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = stringResource(R.string.check), fontSize = 20.sp)
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

        /*  The notes button will be, alternatively, red (notes mode on) or black (notes mode off).
         *  We need the following variable to update the color of the button
        */
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
                /*  activeGameVM keeps a counter of hints requested
                *   (for future implementation, setting to make user able to decide how many hints he can request)
                */
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

    /* The following variable keeps track of which numbers (buttons) to show */
    val numbers = rememberSaveable {
        mutableStateOf(activeGameVM.buttonsNumbers)
    }

    /* The following statement allows to get changes from activeGameVM, as regards which numbers to show */
    activeGameVM.subButtonsNumbers = { numbers.value = it }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (i in 1..9) {

            if (numbers.value.contains(i)) {
                /* we enter this if statement if the i-th button must be showed, so if it's possible to insert the number i into the grid */
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
                /* we enter this if statement if the i-th button must not be showed, so it's replaced by a spacer of the same size */
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

        /* the following is the exact width of the screen */
        val screenWidth = with(LocalDensity.current) {
            constraints.maxWidth.toDp()
        }

        val margin = 15

        Row(
            modifier = Modifier.width(screenWidth - margin.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            when (difficulty) {
                "easy" -> Text(stringResource(id = R.string.easy))
                "medium" -> Text(stringResource(id = R.string.medium))
                "hard" -> Text(stringResource(id = R.string.hard))
            }
            if (settingsVM.getTimerSetting()) {
                Text(text = "Timer: " + stopwatch.formattedTime)
        }
            if (settingsVM.getScoreSetting()) {
                Text(text = stringResource(R.string.score) + ": " + activeGameVM.getScore())
            }
        }
    }
}


