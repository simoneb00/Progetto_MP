package mp.sudoku.ui.view.resume

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.model.Game
import mp.sudoku.ui.view.components.TopBar
import mp.sudoku.viewmodel.Adapter

@Composable
fun GameDetailsLayout(game: Game) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar()
        Title(game)
        GridPreview(game)
    }
}

@Composable
fun GridPreview(game: Game) {
}

@Composable
fun Title(game: Game) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Game ${game.id}",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.secondary
        )
    }
}