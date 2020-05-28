package com.semashko.bookmarks.data.services

import com.semashko.bookmarks.data.api.BookmarksApi
import com.semashko.bookmarks.data.entities.Bookmarks
import org.koin.core.KoinComponent
import org.koin.core.inject

class BookmarksService() : IBookmarksService, KoinComponent {

    private val bookmarksApi: BookmarksApi by inject()

    override fun getBookmarks(): List<Bookmarks>? = bookmarksApi.getBookmarks()
}