package com.semashko.homepage.presentation

import com.semashko.homepage.data.entities.TouristsRoutes

sealed class HomeUiState {
    object Loading : HomeUiState()

    data class Success(val routes: List<TouristsRoutes>) : HomeUiState()

    data class Error(val throwable: Throwable) : HomeUiState()
}
