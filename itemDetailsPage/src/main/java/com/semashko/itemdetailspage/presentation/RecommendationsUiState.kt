package com.semashko.itemdetailspage.presentation

import com.semashko.provider.models.home.Attractions

sealed class RecommendationsUiState {
    object Loading : RecommendationsUiState()

    data class Success(val attractions: List<Attractions>) : RecommendationsUiState()

    data class Error(val throwable: Throwable) : RecommendationsUiState()
}
