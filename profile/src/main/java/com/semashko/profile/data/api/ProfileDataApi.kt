package com.semashko.profile.data.api

import com.google.gson.Gson
import com.semashko.profile.data.entities.User
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request

class ProfileDataApi {
    private val client = OkHttpClient()
    private val gson = Gson()
    private val localId = "123"

    fun getUserProfile(): User? {
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/users/$localId.json")
            .get()
            .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        return gson.fromJson(responseString, User::class.java)
    }
}