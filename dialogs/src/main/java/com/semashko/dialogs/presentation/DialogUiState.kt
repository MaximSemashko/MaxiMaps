package com.semashko.dialogs.presentation

import com.semashko.dialogs.data.entities.Dialog

sealed class DialogUiState {
    object Loading : DialogUiState()

    data class Success(val dialogs: List<Dialog>) : DialogUiState()

    data class Error(val throwable: Throwable) : DialogUiState()
}