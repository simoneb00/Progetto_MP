package mp.sudoku.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.ui.theme.DarkBlue
import mp.sudoku.ui.theme.NormalBlue

@Preview(showBackground = true)
@Composable
fun SettingsLayout() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        CompleteUpperBar()
        SettingsTitle()
        Settings()
    }
}

@Composable
fun SettingsTitle() {
    Row(
        horizontalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 20.dp)
    ) {
        Text(
            text = "Settings",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue
        )
    }
}

@Composable
fun Settings() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 5.dp, bottom = 5.dp)
    ) {
        SettingCard(text = "Setting 1")
        SettingCard(text = "Setting 2")
        SettingCard(text = "Setting 3")
        SettingCard(text = "Setting 4")
        SettingCard(text = "Setting 5")
        SettingCard(text = "Setting 6")
        ThemeCard()
    }
}

@Composable
fun SettingCard(
    text: String
) {
    Card(
        border = BorderStroke(width = 0.5.dp, color = DarkBlue),
        modifier = Modifier.height(50.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = text, fontSize = 18.sp)

            val checkedState = mutableStateOf(true)
            Switch(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it },
                colors = SwitchDefaults.colors(NormalBlue)
            )
        }
    }
}

@Composable
fun ThemeCard() {
    Card(
        border = BorderStroke(width = 0.5.dp, color = DarkBlue),
        modifier = Modifier.height(50.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = "Theme", fontSize = 18.sp)

            var expanded by remember { mutableStateOf(false) }
            DropdownMenu(expanded = expanded,
                onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Blue", fontSize = 15.sp)
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Red", fontSize = 15.sp)
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Black", fontSize = 15.sp)
                }
                DropdownMenuItem(onClick = { /*TODO*/ }) {
                    Text(text = "Orange", fontSize = 15.sp)
                }
            }

            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "icon",
                    tint = NormalBlue,
                    modifier = Modifier.size(40.dp)
                )
            }

        }
    }
}

