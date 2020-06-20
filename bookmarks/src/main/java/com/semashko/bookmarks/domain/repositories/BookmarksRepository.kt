package com.semashko.bookmarks.domain.repositories

import com.semashko.bookmarks.data.entities.Bookmarks
import com.semashko.bookmarks.data.services.IBookmarksService
import com.semashko.extensions.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookmarksRepository(
    private val bookmarksService: IBookmarksService
) : IBookmarksRepository {

    override suspend fun getBookmarks(): Result<List<Bookmarks>> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(bookmarksService.getBookmarks() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }
}