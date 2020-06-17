package com.semashko.comments.presentation

import com.semashko.comments.data.entities.Reviews

sealed class CommentsUiState {
    object Loading : CommentsUiState()

    data class Success(val reviews: List<Reviews>) : CommentsUiState()

    data class Error(val throwable: Throwable) : CommentsUiState()
}