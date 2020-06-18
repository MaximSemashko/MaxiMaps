package com.semashko.profile.presentation

import com.semashko.provider.models.User

sealed class ProfileUiState {
    object Loading : ProfileUiState()

    data class Success(val user: User) : ProfileUiState()

    data class Error(val throwable: Throwable) : ProfileUiState()
}
