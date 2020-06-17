package com.semashko.comments.data.services

import com.semashko.comments.data.entities.Reviews

interface ICommentsService {

    fun getComments(): List<Reviews>?

    fun addComment(review: Reviews): Boolean
}