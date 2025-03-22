package com.example.moodplusplus2.ui.logSummary

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.models.BarData
import com.example.moodplusplus2.data.MoodLogsRepository
import com.example.moodplusplus2.data.SentimentCount
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LogSummaryViewModel(moodLogsRepository: MoodLogsRepository): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLS = 5_000L
    }

    val summaryUiState: StateFlow<LogSummaryUiState> =
        moodLogsRepository.getAllSentimentCount().map { sentimentCount ->
            LogSummaryUiState(
                sentimentCountList = sentimentCount,
                sentimentBarDataList = sentimentCount.toBarData(),
                sentimentPositivityScore = sentimentCount.toPositivityScore()
            )
        }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLS),
                initialValue = LogSummaryUiState()
        )
}

data class  LogSummaryUiState(
    val sentimentCountList: List<SentimentCount> = listOf(),
    var sentimentBarDataList: List<BarData> = listOf(),
    var sentimentPositivityScore: Float = 0f
)

private fun List<SentimentCount>.toPositivityScore(): Float {
    var score: Float = 0f
    var totalCount: Int = 0
    this.forEach {
        val id = it.sentimentID-2130968602
        totalCount += it.count
        score += when(id) {
            0, 3 -> 1
            2, 4 -> -1
            else -> 0
        } * it.count
    }

    return if(totalCount==0) 0f else score/totalCount
}

private fun List<SentimentCount>.toBarData(): List<BarData> {
    val barDataSet: MutableList<BarData> = mutableListOf()
    val sentimentColor = mapOf(
        0 to Color(0xFFFF9800),
        1 to Color(0xFF9E9E9E),
        2 to Color(0xFF2196F3),
        3 to Color(0xFF4CAF50),
        4 to Color(0xFFFF5252),
    )

    val sentimentLabel = mapOf(
        0 to "Exited",
        1 to "Neutral",
        2 to "Sad",
        3 to "Satisfied",
        4 to "Stressed"
    )


    this.forEach { sc ->
        val id = sc.sentimentID-2130968602
        barDataSet.add(
            BarData(
                point = Point(
                    x = id.toFloat(),
                    y = sc.count.toFloat()
                ),
                color = sentimentColor[id]?:(Color(0xFF000000)),
                label = sentimentLabel[id]?:("Others")
            )
        )
    }

    return barDataSet
}