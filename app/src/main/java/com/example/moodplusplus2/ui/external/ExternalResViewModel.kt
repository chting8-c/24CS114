package com.example.moodplusplus2.ui.external

import androidx.lifecycle.ViewModel
import com.example.moodplusplus2.data.ExternalRes

class ExternalResViewModel(allExternalRes: List<ExternalRes>): ViewModel() {
    val uiState: ExternalResUiState = ExternalResUiState(allExternalRes)

}

data class ExternalResUiState(
    val allExternalRes: List<ExternalRes>
) {
    val externalResList: List<ExternalRes> by lazy {allExternalRes}
}