package com.semashko.comments.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semashko.comments.domain.usecases.ICommentsUseCase
import com.semashko.extensions.utils.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CommentsViewModel(
    private val commentsUseCase: ICommentsUseCase
) : ViewModel() {

    private val coroutineScopeJob = Job()
    private val commentsUiMutableState = MutableLiveData<CommentsUiState>()

    val commentsData: LiveData<CommentsUiState>
        get() = commentsUiMutableState

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun loadComments() {
        coroutineContext.cancelChildren()

        commentsUiMutableState.value = CommentsUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = commentsUseCase.getComments()) {
                is Result.Success -> {
                    commentsUiMutableState.value = CommentsUiState.Success(result.value)
                    Log.i("TAG", result.value.toString())
                }
                is Result.Error -> CommentsUiState.Error(result.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}