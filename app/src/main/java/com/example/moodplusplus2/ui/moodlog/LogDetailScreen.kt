package com.example.moodplusplus2.ui.moodlog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodplusplus2.MoodTopAppBar
import com.example.moodplusplus2.R
import com.example.moodplusplus2.data.MoodLog
import com.example.moodplusplus2.ui.AppViewModelProvider
import com.example.moodplusplus2.ui.navigation.NavigationDestination
import com.example.moodplusplus2.ui.theme.MoodPlusPlus2Theme
import kotlinx.coroutines.launch

object LogDetailDestination: NavigationDestination {
    override val route = "log_detail"
    override val titleRes = R.string.log_detail
    const val itemIdArg = "logId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogDetailScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LogDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            MoodTopAppBar(
                title = stringResource(LogDetailDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        MoodLogDetailsBody(
            logDetailsUiState = uiState.value,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteMoodLog()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun MoodLogDetailsBody (
    logDetailsUiState: MoodLogDetailUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var deleteRequired by rememberSaveable { mutableStateOf(false) }
        MoodLogDetails(
            moodLog = logDetailsUiState.moodLogDetail.toMoodLog(),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = {},
            enabled = false,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Share(Upcoming)")
        }
        OutlinedButton(
            onClick = { deleteRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete")
        }
        if (deleteRequired) {
            DeleteDialog(
                onDeleteConfirm = {
                    deleteRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteRequired = false },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
private fun MoodLogDetails(
    moodLog: MoodLog, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(modifier) {
                Text(text = "Date")
                Spacer(modifier = Modifier.weight(1f))
                Text(text = moodLog.date, fontWeight = FontWeight.Bold)
            }
            Row(modifier) {
                Text(text = "Sentiment")
                Spacer(modifier = Modifier.weight(1f))
                Text(text = moodLog.sentiment.toString(), fontWeight = FontWeight.Bold)
            }
            Text(text = "Note:")
            Text(
                text = moodLog.note,
                style = TextStyle.Default.copy(
                    lineBreak = LineBreak.Paragraph
                ),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun DeleteDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Notice") },
        text = { Text("Do you want to delete this log?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("No")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Yes")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MoodLogDetailsScreenPreview() {
    MoodPlusPlus2Theme {
        MoodLogDetailsBody(
            MoodLogDetailUiState(
                moodLogDetail = MoodLogDetails(id = 1, date = "20231222", sentiment = 2, note = "I am fine today"))
            , onDelete = {}
        )
    }
}