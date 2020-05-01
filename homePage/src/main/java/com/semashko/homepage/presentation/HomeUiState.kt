package com.semashko.homepage.presentation

import com.semashko.homepage.domain.entities.HomeModel

sealed class HomeUiState {
    object Loading : HomeUiState()

    data class Success(val routes: HomeModel) : HomeUiState()

    data class Error(val throwable: Throwable) : HomeUiState()
}
