package com.semashko.maximaps.data.api

import com.google.gson.Gson
import com.semashko.maximaps.common.registationUrl
import com.semashko.maximaps.data.entities.SignUpResponse
import com.semashko.maximaps.data.entities.User
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody


class FirebaseRegistration {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun signUpUser(user: User): SignUpResponse? {
        val jsonString = gson.toJson(user).toString()
        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            jsonString
        )

        val request = Request.Builder()
            .url(registationUrl)
            .post(body)
            .build()

        val response = client.newCall(request).execute()

        return if (response.isSuccessful) {
            gson.fromJson(
                response.body().string(),
                SignUpResponse::class.java
            ) ?: null
        } else {
            null
        }
    }

    fun addUserToRealtimeDatabase(user: User, localId: String?): User? {
        val jsonString = gson.toJson(user).toString()
        val body = RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            jsonString
        )

        val url = "https://maximaps.firebaseio.com/users/${localId}.json"

        val request = Request.Builder()
            .url(url)
            .put(body)
            .build()

        val response = client.newCall(request).execute()

        return if (response.isSuccessful) {
            gson.fromJson(
                response.body().string(),
                User::class.java
            ) ?: null
        } else {
            null
        }
    }
}