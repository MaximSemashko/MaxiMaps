package com.semashko.maximaps.pankchat.presentation

import com.semashko.maximaps.pankchat.data.entities.Message

sealed class MessagesUiState {
    object Loading : MessagesUiState()

    data class Success(val messages: List<Message>) : MessagesUiState()

    data class Error(val throwable: Throwable) : MessagesUiState()
}

sealed class ItemUiState {
    object Loading : ItemUiState()

    data class Success(val itemAdded: Boolean) : ItemUiState()

    data class Error(val throwable: Throwable) : ItemUiState()
}