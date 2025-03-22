package com.example.moodplusplus2.ui.moodlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moodplusplus2.data.MoodLog
import com.example.moodplusplus2.data.MoodLogsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class LogHomeViewModel(moodLogsRepository: MoodLogsRepository): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLS = 5_000L
    }

    val homeUiState: StateFlow<LogHomeUiState> =
        moodLogsRepository.getAllMoodLogsStream().map {LogHomeUiState(it)}
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLS),
                initialValue = LogHomeUiState()
            )
}

data class  LogHomeUiState(val logList: List<MoodLog> = listOf())