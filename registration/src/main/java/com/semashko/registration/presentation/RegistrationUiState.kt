package com.semashko.registration.presentation

import com.semashko.registration.data.entities.SignUpResponse

sealed class RegistrationUiState {
    object Loading : RegistrationUiState()

    data class Success(val result: SignUpResponse) : RegistrationUiState()

    data class Error(val throwable: Throwable) : RegistrationUiState()
}
