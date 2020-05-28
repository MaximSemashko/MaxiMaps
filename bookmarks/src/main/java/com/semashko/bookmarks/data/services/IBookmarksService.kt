package com.semashko.bookmarks.data.services

import com.semashko.bookmarks.data.entities.Bookmarks

interface IBookmarksService {

    fun getBookmarks(): List<Bookmarks>?
}