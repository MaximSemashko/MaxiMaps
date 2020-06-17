package com.semashko.comments.domain.repositories

import com.semashko.comments.data.entities.Reviews
import com.semashko.comments.data.services.ICommentsService
import com.semashko.extensions.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentsRepository(
    private val commentsService: ICommentsService
) : ICommentsRepository {

    override suspend fun getComments(): Result<List<Reviews>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(commentsService.getComments() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }

    override suspend fun addComment(reviews: Reviews): Result<Boolean> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(commentsService.addComment(reviews))
            }.getOrElse {
                Result.Error(it)
            }
        }
    }
}