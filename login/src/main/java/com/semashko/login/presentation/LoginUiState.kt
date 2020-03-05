package com.semashko.login.presentation

import com.semashko.login.data.entities.LoginResponse

sealed class LoginUiState {
    object Loading : LoginUiState()

    data class Success(val result: LoginResponse) : LoginUiState()

    data class Error(val throwable: Throwable) : LoginUiState()
}
