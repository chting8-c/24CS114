package com.example.moodplusplus2.ui.moodlog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodplusplus2.data.MoodLogsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LogDetailViewModel(
    saveStateHandle: SavedStateHandle,
    private val moodLogsRepository: MoodLogsRepository
): ViewModel() {

    private val moodLogId: Int = checkNotNull(saveStateHandle[LogDetailDestination.itemIdArg])

    val uiState: StateFlow<MoodLogDetailUiState> =
        moodLogsRepository.getMoodLogStream(moodLogId)
            .filterNotNull()
            .map{
                MoodLogDetailUiState(moodLogDetail = it.toMoodLogDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MoodLogDetailUiState()
            )

    suspend fun deleteMoodLog() {
        moodLogsRepository.deleteMoodLog(uiState.value.moodLogDetail.toMoodLog())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class  MoodLogDetailUiState(
    val moodLogDetail: MoodLogDetails = MoodLogDetails()
)