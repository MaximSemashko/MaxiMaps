package com.semashko.bookmarks.domain.usecases

import com.semashko.bookmarks.data.entities.Bookmarks
import com.semashko.extensions.utils.Result

interface IBookmarksUseCase {

    suspend fun getBookmarks(): Result<List<Bookmarks>>
}