package com.semashko.bookmarks.presentation

import com.semashko.bookmarks.data.entities.Bookmarks

sealed class BookmarksUiState {
    object Loading : BookmarksUiState()

    data class Success(val bookmarks: List<Bookmarks>) : BookmarksUiState()

    data class Error(val throwable: Throwable) : BookmarksUiState()
}