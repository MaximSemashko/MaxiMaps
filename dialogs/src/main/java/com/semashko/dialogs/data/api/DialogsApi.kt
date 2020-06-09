package com.semashko.dialogs.data.api

import com.google.gson.Gson
import com.semashko.dialogs.data.entities.Dialog
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.json.JSONObject

class DialogsApi {
    private val client = OkHttpClient()
    private val gson = Gson()
    private val localId = "123"

    fun getDialogs(): List<Dialog>? {
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/${localId}/dialogs.json")
            .get()
            .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        return if (responseString == "null") {
            emptyList()
        } else {
            val bookmarks = mutableListOf<Dialog>()

            val objects = JSONObject(responseString)
            val iterator = objects.keys()

            while (iterator.hasNext()) {
                val bookmark = objects.getJSONObject(iterator.next())

                bookmarks.add(gson.fromJson(bookmark.toString(), Dialog::class.java))
            }

            bookmarks
        }
    }
}