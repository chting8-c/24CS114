package com.example.moodplusplus2.ui.moodlog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodplusplus2.MoodBottomAppBar
import com.example.moodplusplus2.MoodTopAppBar
import com.example.moodplusplus2.R
import com.example.moodplusplus2.data.MoodLog
import com.example.moodplusplus2.data.Sentiment
import com.example.moodplusplus2.ui.AppViewModelProvider
import com.example.moodplusplus2.ui.navigation.BottomNavigationDestination
import com.example.moodplusplus2.ui.theme.MoodPlusPlus2Theme

object LogHomeDestination: BottomNavigationDestination {
    override val route = "LogHome"
    override val titleRes = R.string.list_log
    override val iconRes = R.drawable.bottom_mood_log
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogHomeScreen(
    navigateToMoodLogInput: () -> Unit,
    navigateToMoodLogUpdate: (Int) -> Unit,
    botNavBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LogHomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MoodTopAppBar(
                title = stringResource(LogHomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToMoodLogInput,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.Moodlog_Entry_Title)
                )
            }
        },
        bottomBar = {
            botNavBar()
        }
    ) { innerPadding ->
        HomeBody(
            moodLogList = homeUiState.logList,
            onMoodLogClick = navigateToMoodLogUpdate,
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
    }
}


@Composable
private fun HomeBody(
    moodLogList: List<MoodLog>,
    onMoodLogClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (moodLogList.isEmpty()) {
            Spacer(modifier = Modifier.height(56.dp))
            Text(
                text = stringResource(R.string.no_mood_logs_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding)
            )
        } else {
            MoodLogList(
                moodLogList = moodLogList,
                onMoodLogClick = { onMoodLogClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }

}

@Composable
private fun MoodLogList(
    moodLogList: List<MoodLog>,
    onMoodLogClick: (MoodLog) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = moodLogList, key = {it.id}) {moodLog ->
            MoodLogCard(moodLog = moodLog,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onMoodLogClick(moodLog) }
            )
        }
    }
}

@Composable
private fun MoodLogCard (
    moodLog: MoodLog,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = moodLog.date,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(moodLog.sentiment.icon),
                contentDescription = moodLog.sentiment.toString()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoodLogCardPreview() {
    MoodPlusPlus2Theme {
        MoodLogCard(
            MoodLog(id = 1, date = "20231212", sentiment = Sentiment.Satisfied, note = "I am fine today.")
        )
    }
}