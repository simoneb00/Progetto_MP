package mp.sudoku.ui.view

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mp.sudoku.R


@Composable
fun RulesLayout() {

    /* background color transition animation

    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val color = animateColorAsState(
        targetValue = if (animationPlayed) BackgroundWhite else Color.White,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 0
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

     end of animation */

    TopBar(includeGuideButton = false, includeSettingsButton = false, //backgroundColor = color.value
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
            //.background(color = color.value)
                ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val image: Painter = painterResource(id = R.drawable.rules)
        Image(painter = image, contentDescription = "Rules Image", modifier = Modifier.size(300.dp).background(color = Color.White))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {
            RuleCard(
                text = stringResource(R.string.rule1_description),
                title = stringResource(R.string.rule1),
                subtitle = stringResource(R.string.rule1_subtitle)
            )

            RuleCard(
                text = stringResource(R.string.rule2_description),
                title = stringResource(R.string.rule2),
                subtitle = stringResource(R.string.rule2_subtitle)
            )

            RuleCard(
                text = stringResource(R.string.rule3_description),
                title = stringResource(R.string.rule3),
                subtitle = stringResource(R.string.rule3_subtitle)
            )

            RuleCard(
                text = stringResource(R.string.rule4_description),
                title = stringResource(R.string.rule4),
                subtitle = stringResource(R.string.rule4_subtitle)
            )
        }

    }
}

@Composable
fun RuleCard(
    text: String,
    title: String,
    subtitle: String,
    textColor: Color = Color.Black
) {

    /* animation */

    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val height = animateDpAsState(
        targetValue = if (animationPlayed) 160.dp else 0.dp,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 0
        )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 15.dp)) {

        Text(text = title, fontSize = 17.sp, fontWeight = FontWeight.Bold,
            //color = MaterialTheme.colors.secondary
            )        /* Rule no. * */

        Text(text = subtitle, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colors.secondary)     /* short description of the rule */

        Card(
            modifier = Modifier
                .fillMaxWidth()
                //.background(color = Color.White)
                .height(height = height.value)
        ) {
            Text(text = text, fontSize = 15.sp,
                //color = textColor,
                modifier = Modifier.padding(5.dp))       /* long description of the rule */
        }
    }

}