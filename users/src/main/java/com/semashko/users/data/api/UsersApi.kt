package com.semashko.users.data.api

import com.google.gson.Gson
import com.semashko.provider.models.User
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.json.JSONObject

class UsersApi {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun getUsers(): List<User>? {
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/users.json")
            .get()
            .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        return if (responseString == "null") {
            emptyList()
        } else {
            val user = mutableListOf<User>()

            val objects = JSONObject(responseString)
            val iterator = objects.keys()

            while (iterator.hasNext()) {
                val bookmark = objects.getJSONObject(iterator.next())

                user.add(gson.fromJson(bookmark.toString(), User::class.java))
            }

            user
        }
    }
}