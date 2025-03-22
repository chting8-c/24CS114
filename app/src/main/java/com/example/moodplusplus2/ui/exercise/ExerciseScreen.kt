package com.example.moodplusplus2.ui.exercise

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodplusplus2.MoodTopAppBar
import com.example.moodplusplus2.R
import com.example.moodplusplus2.ui.AppViewModelProvider
import com.example.moodplusplus2.ui.navigation.BottomNavigationDestination
import com.example.moodplusplus2.ui.theme.MoodPlusPlus2Theme

object ExerciseDestination: BottomNavigationDestination {
    override val route = "Exercise"
    override val titleRes = R.string.exercise
    override val iconRes = R.drawable.bottom_self_improvement
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    botNavBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExerciseViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showDialog by remember {mutableStateOf(false)}

    Scaffold(
        modifier = modifier,
        topBar = {
            MoodTopAppBar(
                title = stringResource(ExerciseDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {showDialog = true},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Meditation_Info"
                )
            }
        },
        bottomBar = {
            botNavBar()
        }
    ) { innerPadding ->
        ExerciseBody(
            viewModel = viewModel,
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
        if(showDialog) {
            TutorialDialog (onDismissRequest = {showDialog = false})
        }

    }

}

@Composable
private fun ExerciseBody(
    viewModel: ExerciseViewModel,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewModel.apply {
            Text(
                text = timerText.value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 72.sp
            )
            Spacer(Modifier.height(72.dp))
            Row() {
                Button(
                    onClick = {decreaseTimerTime()}
                ) {
                    Text("- 1min")
                }
                Spacer(Modifier.width(10.dp))
                Button(
                    onClick = {increaseTimerTime()}
                ) {
                    Text("+ 1min")
                }
            }
            Button(
                onClick = {
                    if(isCounting.value) stopTimer() else startTimer()
                }
            ) {
                Text(
                    text = if(isCounting.value) "Stop Meditation" else "Start Meditation"
                )
            }
            Button(onClick = {resetTimer()}) {
                Text(
                    text = "Reset Timer"
                )
            }
        }
    }
}

@Composable
fun TutorialDialog(onDismissRequest: () -> Unit) {

    Dialog(onDismissRequest = {onDismissRequest()}) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .padding(16.dp)
                .clickable{onDismissRequest()},
            shape = RoundedCornerShape(14.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
                ) {
                Text(
                    text = "How To Start Meditation\n",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(
                    modifier = Modifier.padding(bottom = 12.dp),
                    thickness = 2.dp,
                    color = Color.Black
                )
                Text(
                    text =  "Posture: Sit comfortably, hands rested\n" +
                            "Environment: A space with minimal distraction\n" +
                            "Focus: Close eyes, breathe naturally. Notice how the air flow through your body.\n" +
                            "Mindfulness: When thought arise, dismiss and return to your focus",
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = 2.em
                )
                Text(
                    text = "(Tap To Close)",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.alpha(0.5f).padding(top = 24.dp)
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun TutorialPreview() {
    MoodPlusPlus2Theme {
        TutorialDialog(onDismissRequest = {})
    }
}