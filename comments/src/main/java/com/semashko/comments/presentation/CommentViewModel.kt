package com.semashko.comments.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semashko.provider.models.detailsPage.Reviews
import com.semashko.comments.domain.usecases.ICommentsUseCase
import com.semashko.extensions.utils.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CommentViewModel(
    private val commentsUseCase: ICommentsUseCase
) : ViewModel() {

    private val coroutineScopeJob = Job()
    private val commentUiMutableState = MutableLiveData<CommentUiState>()

    val commentData: LiveData<CommentUiState>
        get() = commentUiMutableState

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun loadComment(review: Reviews) {
        coroutineContext.cancelChildren()

        commentUiMutableState.value = CommentUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = commentsUseCase.addComment(reviews = review)) {
                is Result.Success -> {
                    commentUiMutableState.value = CommentUiState.Success(result.value)
                }
                is Result.Error -> CommentUiState.Error(result.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}