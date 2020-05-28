package com.semashko.bookmarks.koin

import com.semashko.bookmarks.data.api.BookmarksApi
import com.semashko.bookmarks.data.services.BookmarksService
import com.semashko.bookmarks.data.services.IBookmarksService
import com.semashko.bookmarks.domain.repositories.BookmarksRepository
import com.semashko.bookmarks.domain.repositories.IBookmarksRepository
import com.semashko.bookmarks.domain.usecases.BookmarksUseCase
import com.semashko.bookmarks.domain.usecases.IBookmarksUseCase
import com.semashko.bookmarks.presentation.BookmarksViewModel
import com.semashko.bookmarks.presentation.fragments.BookmarksFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val bookmarksModule = module {
    factory {
        BookmarksApi()
    }

    scope(named<BookmarksFragment>()) {
        scoped<IBookmarksService> { BookmarksService() }
        scoped<IBookmarksRepository> {
            BookmarksRepository(
                bookmarksService = get()
            )
        }
        scoped<IBookmarksUseCase> {
            BookmarksUseCase(
                bookmarksRepository = get()
            )
        }
        viewModel {
            BookmarksViewModel(
                bookmarksUseCase = get()
            )
        }
    }
}
