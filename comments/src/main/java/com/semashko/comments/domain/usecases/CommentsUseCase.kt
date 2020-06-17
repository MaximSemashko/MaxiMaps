package com.semashko.comments.domain.usecases

import com.semashko.comments.data.entities.Reviews
import com.semashko.comments.domain.repositories.ICommentsRepository
import com.semashko.extensions.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class CommentsUseCase(
    private val repository: ICommentsRepository
) : ICommentsUseCase {

    override suspend fun getComments(): Result<List<Reviews>> {
        return coroutineScope {
            val commentsAsync = async { repository.getComments() }

            val comments = when (val result = commentsAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> emptyList()
            }

            Result.Success(comments)
        }
    }

    override suspend fun addComment(reviews: Reviews): Result<Boolean> {
        return coroutineScope {
            val itemAddedAsync = async {
                repository.addComment(reviews)
            }

            val itemAdded = when (val result = itemAddedAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> false
            }

            Result.Success(itemAdded)
        }
    }
}