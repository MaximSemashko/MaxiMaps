package com.semashko.bookmarks.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semashko.bookmarks.domain.usecases.IBookmarksUseCase
import com.semashko.extensions.utils.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class BookmarksViewModel(
    private val bookmarksUseCase: IBookmarksUseCase
) : ViewModel() {

    private val coroutineScopeJob = Job()
    private val bookmarksUiMutableState = MutableLiveData<BookmarksUiState>()

    val bookmarksData: LiveData<BookmarksUiState>
        get() = bookmarksUiMutableState

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun load() {
        coroutineContext.cancelChildren()

        bookmarksUiMutableState.value = BookmarksUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = bookmarksUseCase.getBookmarks()) {
                is Result.Success -> {
                    bookmarksUiMutableState.value = BookmarksUiState.Success(result.value)
                    Log.i("TAG", result.value.toString())
                }
                is Result.Error -> BookmarksUiState.Error(result.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}