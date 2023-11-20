package uk.ac.tees.w9623063.myapplication.utils

import uk.ac.tees.w9623063.myapplication.domain.network.model.Result

sealed class LiveScoreUiState {
    data class Success(val list: List<Result>): LiveScoreUiState()
    data class Loading(val isLoading: Boolean): LiveScoreUiState()
    data class Error(val exception: Throwable): LiveScoreUiState()
}