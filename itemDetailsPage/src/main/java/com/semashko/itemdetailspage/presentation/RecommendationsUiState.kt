package com.semashko.itemdetailspage.presentation

import com.semashko.provider.models.home.Attractions

sealed class RecommendationsUiState {
    object Loading : RecommendationsUiState()

    data class Success(val attractions: List<Attractions>) : RecommendationsUiState()

    data class Error(val throwable: Throwable) : RecommendationsUiState()
}

sealed class ItemUiState {
    object Loading : ItemUiState()

    data class Success(val itemAdded: Boolean) : ItemUiState()

    data class Error(val throwable: Throwable) : ItemUiState()
}
