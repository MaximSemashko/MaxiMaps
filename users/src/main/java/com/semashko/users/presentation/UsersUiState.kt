package com.semashko.users.presentation

import com.semashko.provider.models.User

sealed class UsersUiState {
    object Loading : UsersUiState()

    data class Success(val bookmarks: List<User>) : UsersUiState()

    data class Error(val throwable: Throwable) : UsersUiState()
}