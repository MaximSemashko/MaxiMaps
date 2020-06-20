package com.semashko.comments.presentation

import com.semashko.provider.models.detailsPage.Reviews

sealed class CommentsUiState {
    object Loading : CommentsUiState()

    data class Success(val reviews: List<Reviews>) : CommentsUiState()

    data class Error(val throwable: Throwable) : CommentsUiState()
}

sealed class CommentUiState {
    object Loading : CommentUiState()

    data class Success(val itemWasAdded: Boolean) : CommentUiState()

    data class Error(val throwable: Throwable) : CommentUiState()

}