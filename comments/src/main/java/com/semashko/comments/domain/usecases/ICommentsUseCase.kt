package com.semashko.comments.domain.usecases

import com.semashko.provider.models.detailsPage.Reviews
import com.semashko.extensions.utils.Result

interface ICommentsUseCase {

    suspend fun getComments(): Result<List<Reviews>>

    suspend fun addComment(reviews: Reviews): Result<Boolean>
}