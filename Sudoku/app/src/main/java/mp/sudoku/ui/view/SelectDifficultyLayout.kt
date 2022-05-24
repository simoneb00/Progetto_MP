package mp.sudoku.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.ui.theme.NormalBlue

@Preview(showBackground = true)
@Composable
fun DifficultyLayout() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        UpperBar(true)
        DifficultyText()
        DifficultyButtons()
    }
}

@Composable
fun DifficultyText() {
    Row(modifier = Modifier.fillMaxWidth().padding(top = 100.dp), horizontalArrangement = Arrangement.Center) {
        Text(text = "Select Difficulty", fontSize = 30.sp, fontWeight = FontWeight.SemiBold ,color = NormalBlue)
    }
}

@Composable
fun DifficultyButtons() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 125.dp)
            .height(210.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationButton(text = "Easy", value = 2)
        NavigationButton(text = "Normal", value = 2)
        NavigationButton(text = "Hard", value = 2)
    }
}