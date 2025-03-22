package com.example.moodplusplus2.ui.moodlog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.yml.charts.common.extensions.isNotNull
import com.example.moodplusplus2.R
import com.example.moodplusplus2.data.MoodLog
import com.example.moodplusplus2.data.MoodLogsRepository
import com.example.moodplusplus2.data.Sentiment

class LogInputViewModel(private val moodLogsRepository: MoodLogsRepository): ViewModel() {
    var moodLogUiState by mutableStateOf(MoodLogUiState())
        private set

    fun updateUiState(moodLogDetails: MoodLogDetails) {
        moodLogUiState =
            MoodLogUiState(moodLogDetails = moodLogDetails, isEntryValid = validateInput(moodLogDetails))
    }

    private fun validateInput(uiState: MoodLogDetails = moodLogUiState.moodLogDetails): Boolean {
        return with(uiState) {
            date.isNotBlank() && date.length == 8 && sentiment>0 && note.isNotBlank()
        }
    }

    suspend fun saveMoodLog() {
        if(validateInput()) {
            moodLogsRepository.insertMoodLog(moodLogUiState.moodLogDetails.toMoodLog())
        }
    }
}

data class MoodLogUiState(
    val moodLogDetails: MoodLogDetails = MoodLogDetails(),
    val isEntryValid: Boolean = false
)

data class MoodLogDetails(
    val id: Int = 0,
    val date: String = "",
    val sentiment: Int = 0,
    val note: String = ""
)

fun MoodLogDetails.toMoodLog(): MoodLog = MoodLog(
    id = id,
    date = date,
    sentiment = intToSentiment(sentiment),
    note = note
)

//convert [MoodLog] to [MoodLogUiState]
fun MoodLog.toMoodLogUiState(isEntryValid: Boolean = false): MoodLogUiState = MoodLogUiState(
    moodLogDetails = this.toMoodLogDetails(),
    isEntryValid = isEntryValid
)

//convert [MoodLogUiState] to [MoodLog]
fun MoodLog.toMoodLogDetails(): MoodLogDetails = MoodLogDetails(
    id = id,
    date = date,
    sentiment = sentimentToInt(sentiment),
    note = note
)

fun intToSentiment(sentNum: Int): Sentiment {
    return when(sentNum) {
        1 -> Sentiment.Satisfied
        2 -> Sentiment.Excited
        3 -> Sentiment.Neutral
        4 -> Sentiment.Sad
        5 -> Sentiment.Stressed
        else -> Sentiment.Neutral
    }
}

fun sentimentToInt(sentiment: Sentiment): Int {
    return when(sentiment) {
        Sentiment.Satisfied -> 1
        Sentiment.Excited -> 2
        Sentiment.Neutral -> 3
        Sentiment.Sad -> 4
        Sentiment.Stressed -> 5
    }
}