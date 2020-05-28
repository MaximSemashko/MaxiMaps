package com.semashko.bookmarks.domain.usecases

import com.semashko.bookmarks.data.entities.Bookmarks
import com.semashko.bookmarks.domain.repositories.IBookmarksRepository
import com.semashko.extensions.utils.Result
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class BookmarksUseCase(
    private val bookmarksRepository: IBookmarksRepository
) : IBookmarksUseCase {

    override suspend fun getBookmarks(): Result<List<Bookmarks>> {
        return coroutineScope {
            val bookmarksAsync = async { bookmarksRepository.getBookmarks() }

            val bookmarks = when (val result = bookmarksAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> emptyList()
            }

            Result.Success(bookmarks)
        }
    }
}