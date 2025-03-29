package com.example.moodplusplus2.ui.logSummary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarChartType
import co.yml.charts.ui.barchart.models.BarData
import com.example.moodplusplus2.MoodTopAppBar
import com.example.moodplusplus2.R
import com.example.moodplusplus2.ui.AppViewModelProvider
import com.example.moodplusplus2.ui.navigation.BottomNavigationDestination

object LogSummaryDestination: BottomNavigationDestination {
    override val route = "LogSummary"
    override val titleRes = R.string.summary
    override val iconRes = R.drawable.bottom_summarize
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogSummaryScreen(
    botNavBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LogSummaryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val summaryUiState by viewModel.summaryUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MoodTopAppBar(
                title = stringResource(LogSummaryDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        }, bottomBar = {
            botNavBar()
        }
    ) { innerPadding ->
        SummaryBody(
            summaryUiState = summaryUiState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        )
    }
}

@Composable
private fun SummaryBody(
    summaryUiState: LogSummaryUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if(summaryUiState.isEmpty) {
            Spacer(modifier = Modifier.height(56.dp))
            Text(
                text = "No data to generate summary!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding)
            )
        } else {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Sentiment Count",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding)
            )
            AllTimeSentimentCountChart(
                barsData = summaryUiState.sentimentBarDataList,
            )
            Spacer(modifier = Modifier.height(12.dp))
            SentimentPositivity(
                score = summaryUiState.sentimentPositivityScore
            )
        }
    }
}


@Composable
private fun AllTimeSentimentCountChart(
    barsData: List<BarData>,
) {
    val stepSize = 6

    val xAxisData = AxisData.Builder()
        .axisStepSize(50.dp)
        .steps(barsData.indices.last)
        .bottomPadding(10.dp)
        .axisLabelAngle(10f)
        .labelData { index ->
            if (index < barsData.size) barsData[index].label
            else ""
        }
        .startDrawPadding(25.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(stepSize)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index -> (index * (12 / stepSize)).toString() }
        .build()

    val barChartData = BarChartData(
        chartData = barsData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        paddingEnd = 20.dp,
        barChartType =  BarChartType.VERTICAL
    )

    BarChart(
        modifier = Modifier.height(320.dp)
        , barChartData = barChartData
    )
}

@Composable
private fun SentimentPositivity(
    score: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(12.dp)
    ) {
        Text(
            text = "Your Mood Positivity Is:",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = when{
                score > 0.3f -> "😊 Positive"
                score < (-0.3f) -> "😞 Negative"
                else -> "😐 Neutral"
                }
        )
    }
}