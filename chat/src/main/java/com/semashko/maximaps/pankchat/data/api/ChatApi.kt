package com.semashko.maximaps.pankchat.data.api

import com.google.gson.Gson
import com.semashko.maximaps.pankchat.data.entities.Message
import com.semashko.provider.models.detailsPage.ItemDetails
import com.semashko.provider.preferences.IUserInfoPreferences
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.IOException

class ChatApi : KoinComponent {
    private val userInfoPreferences: IUserInfoPreferences by inject()

    private val client = OkHttpClient()
    private val gson = Gson()

    fun getMessages(): List<Message>? {
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/${userInfoPreferences.localId}/Chats.json")
            .get()
            .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        return if (responseString == "null") {
            emptyList()
        } else {
            val bookmarks = mutableListOf<Message>()

            val objects = JSONObject(responseString)
            val iterator = objects.keys()

            while (iterator.hasNext()) {
                val bookmark = objects.getJSONObject(iterator.next())

                bookmarks.add(gson.fromJson(bookmark.toString(), Message::class.java))
            }

            bookmarks
        }
    }

    @Throws(IOException::class)
    fun sendMessage(message: Message, localId: String?): Boolean {
        val jsonString = gson.toJson(message).toString()
        val body =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/${localId}/Chats.json")
            .post(body)
            .build()

        val response = client.newCall(request).execute()

        return response.isSuccessful
    }
}