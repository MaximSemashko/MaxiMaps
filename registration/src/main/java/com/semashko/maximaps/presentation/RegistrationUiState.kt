package com.semashko.maximaps.presentation

import com.semashko.maximaps.data.entities.SignUpResponse

sealed class RegistrationUiState {
    object Loading : RegistrationUiState()

    data class Success(val result: SignUpResponse) : RegistrationUiState()

    data class Error(val throwable: Throwable) : RegistrationUiState()
}
