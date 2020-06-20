package com.semashko.comments.data.services

import com.semashko.comments.data.api.CommentsApi
import com.semashko.provider.models.detailsPage.Reviews
import org.koin.core.KoinComponent
import org.koin.core.inject

class CommentsService() : ICommentsService, KoinComponent {

    private val api: CommentsApi by inject()

    override fun getComments(): List<Reviews>? = api.getComments()

    override fun addComment(review: Reviews): Boolean = api.addComment(review)
}