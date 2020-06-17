package com.semashko.comments.domain.repositories

import com.semashko.comments.data.entities.Reviews
import com.semashko.extensions.utils.Result

interface ICommentsRepository {

    suspend fun getComments(): Result<List<Reviews>>

    suspend fun addComment(reviews: Reviews): Result<Boolean>
}