package com.semashko.searchfragment.presentation

import com.semashko.searchfragment.data.entitites.SearchModel

sealed class SearchUiState {
    object Loading : SearchUiState()
    object NewSearch : SearchUiState()
    object Pagination : SearchUiState()
    data class Success(val result: SearchModel) : SearchUiState()
    data class Error(val throwable: Throwable) : SearchUiState()
}