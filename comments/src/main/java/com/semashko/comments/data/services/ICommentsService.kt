package com.semashko.comments.data.services

import com.semashko.provider.models.detailsPage.Reviews

interface ICommentsService {

    fun getComments(): List<Reviews>?

    fun addComment(review: Reviews): Boolean
}