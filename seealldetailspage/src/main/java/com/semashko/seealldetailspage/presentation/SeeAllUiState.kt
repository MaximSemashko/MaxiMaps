package com.semashko.seealldetailspage.presentation

import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes

sealed class MansionsUiState {
    object Loading : MansionsUiState()

    data class Success(val mansions: List<Mansions>) : MansionsUiState()

    data class Error(val throwable: Throwable) : MansionsUiState()
}

sealed class RoutesUiState {
    object Loading : RoutesUiState()

    data class Success(val routes: List<TouristsRoutes>) : RoutesUiState()

    data class Error(val throwable: Throwable) : RoutesUiState()
}

sealed class AttractionsUiState {
    object Loading : AttractionsUiState()

    data class Success(val mansions: List<Attractions>) : AttractionsUiState()

    data class Error(val throwable: Throwable) : AttractionsUiState()
}
