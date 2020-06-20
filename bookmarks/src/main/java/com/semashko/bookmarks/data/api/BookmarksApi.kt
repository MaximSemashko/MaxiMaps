package com.semashko.bookmarks.data.api

import com.google.gson.Gson
import com.semashko.bookmarks.data.entities.Bookmarks
import com.semashko.provider.preferences.IUserInfoPreferences
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject

class BookmarksApi : KoinComponent {
    private val userInfoPreferences: IUserInfoPreferences by inject()

    private val client = OkHttpClient()
    private val gson = Gson()

    fun getBookmarks(): List<Bookmarks>? {
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/${userInfoPreferences.localId}/Bookmarks.json")
            .get()
            .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        return if (responseString == "null") {
            emptyList()
        } else {
            val bookmarks = mutableListOf<Bookmarks>()

            val objects = JSONObject(responseString)
            val iterator = objects.keys()

            while (iterator.hasNext()) {
                val bookmark = objects.getJSONObject(iterator.next())

                bookmarks.add(gson.fromJson(bookmark.toString(), Bookmarks::class.java))
            }

            bookmarks
        }
    }
}