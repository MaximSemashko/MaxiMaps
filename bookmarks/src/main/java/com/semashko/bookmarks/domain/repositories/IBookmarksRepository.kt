package com.semashko.bookmarks.domain.repositories

import com.semashko.bookmarks.data.entities.Bookmarks
import com.semashko.extensions.utils.Result

interface IBookmarksRepository {

    suspend fun getBookmarks(): Result<List<Bookmarks>>
}