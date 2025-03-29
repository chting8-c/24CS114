package com.example.moodplusplus2.ui.moodlog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodplusplus2.MoodTopAppBar
import com.example.moodplusplus2.R
import com.example.moodplusplus2.ui.AppViewModelProvider
import com.example.moodplusplus2.ui.navigation.NavigationDestination
import com.example.moodplusplus2.ui.theme.MoodPlusPlus2Theme
import kotlinx.coroutines.launch

object LogInputDestination: NavigationDestination {
    override val route = "log_entry"
    override val titleRes = R.string.log_input
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInputScreen(
    navigationBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: LogInputViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar =  {
            MoodTopAppBar(
                title = stringResource(LogInputDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        LogInputBody(
            moodLogUiState = viewModel.moodLogUiState,
            onMoodLogValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveMoodLog()
                    navigationBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun LogInputBody(
    moodLogUiState: MoodLogUiState,
    onMoodLogValueChange: (MoodLogDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.padding(16.dp)
    ) {
        LogInputForm(
            moodLogDetails = moodLogUiState.moodLogDetails,
            onValueChange = onMoodLogValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = moodLogUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun LogInputForm(
    moodLogDetails: MoodLogDetails,
    modifier: Modifier = Modifier,
    onValueChange: (MoodLogDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = moodLogDetails.date,
            onValueChange = { onValueChange(moodLogDetails.copy(date = it)) },
            label = { Text("Date in YYYYMMDD* ") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        SentimentSelection(
            moodLogDetails = moodLogDetails,
            onValueChange = { onValueChange(moodLogDetails.copy(sentiment = it))},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = moodLogDetails.note,
            onValueChange = { onValueChange(moodLogDetails.copy(note = it)) },
            label = { Text("What Happened That Day?*") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            maxLines = 5,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
    }
}

@Composable
fun SentimentSelection(
    moodLogDetails: MoodLogDetails,
    onValueChange: (Int) -> Unit,
    modifier: Modifier
) {
    val radioOptions = listOf(
        "Satisfied" to 1,
        "Excited" to 2,
        "Neutral" to 3,
        "Sad" to 4,
        "Stressed" to 5
    )

    Column(modifier.selectableGroup().background(color = MaterialTheme.colorScheme.secondaryContainer)) {
        Text(
            text="How do you feel today?*",
            fontSize = 14.sp
        )
        radioOptions.forEach { (text, id) ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (id == moodLogDetails.sentiment),
                        onClick = {onValueChange(id)},
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (id == moodLogDetails.sentiment),
                    onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun LogInputScreenPreview() {
    MoodPlusPlus2Theme {
        LogInputBody(moodLogUiState = MoodLogUiState(
            MoodLogDetails(
                date = "20241222", sentiment = 1, note = "Today is fine."
            )
        ), onMoodLogValueChange = {}, onSaveClick = {})
    }
}