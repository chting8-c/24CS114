package com.example.moodplusplus2.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.moodplusplus2.MoodApplication
import com.example.moodplusplus2.data.LocalExternalResDataProvider
import com.example.moodplusplus2.ui.exercise.ExerciseViewModel
import com.example.moodplusplus2.ui.external.ExternalResViewModel
import com.example.moodplusplus2.ui.moodlog.LogDetailViewModel
import com.example.moodplusplus2.ui.moodlog.LogHomeViewModel
import com.example.moodplusplus2.ui.moodlog.LogInputViewModel
import com.example.moodplusplus2.ui.logSummary.LogSummaryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            LogDetailViewModel(
                this.createSavedStateHandle(),
                moodApplication().container.moodLogsRepository
            )
        }
        initializer {
            LogHomeViewModel(moodApplication().container.moodLogsRepository)
        }
        initializer {
            LogInputViewModel(moodApplication().container.moodLogsRepository)
        }
        initializer {
            LogSummaryViewModel(
                moodApplication().container.moodLogsRepository
                //LocalSentimentCountDataProvider.allSentimentCount
            )
        }
        initializer {
            ExerciseViewModel()
        }
        initializer {
            ExternalResViewModel(LocalExternalResDataProvider.allExternalRes)
        }
    }

}

fun CreationExtras.moodApplication(): MoodApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MoodApplication)