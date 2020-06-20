package com.semashko.comments.koin

import com.semashko.comments.data.api.CommentsApi
import com.semashko.comments.data.services.CommentsService
import com.semashko.comments.data.services.ICommentsService
import com.semashko.comments.domain.repositories.CommentsRepository
import com.semashko.comments.domain.repositories.ICommentsRepository
import com.semashko.comments.domain.usecases.CommentsUseCase
import com.semashko.comments.domain.usecases.ICommentsUseCase
import com.semashko.comments.presentation.CommentViewModel
import com.semashko.comments.presentation.CommentsViewModel
import com.semashko.comments.presentation.fragments.AddCommentFragment
import com.semashko.comments.presentation.fragments.CommentsFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val commentsModule = module {
    factory {
        CommentsApi()
    }

    scope(named<CommentsFragment>()) {
        scoped<ICommentsService> { CommentsService() }
        scoped<ICommentsRepository> {
            CommentsRepository(
                commentsService = get()
            )
        }
        scoped<ICommentsUseCase> {
            CommentsUseCase(
                repository = get()
            )
        }
        viewModel {
            CommentsViewModel(
                commentsUseCase = get()
            )
        }
    }

    scope(named<AddCommentFragment>()) {
        scoped<ICommentsService> { CommentsService() }
        scoped<ICommentsRepository> {
            CommentsRepository(
                commentsService = get()
            )
        }
        scoped<ICommentsUseCase> {
            CommentsUseCase(
                repository = get()
            )
        }
        viewModel {
            CommentViewModel(
                commentsUseCase = get()
            )
        }

    }
}
