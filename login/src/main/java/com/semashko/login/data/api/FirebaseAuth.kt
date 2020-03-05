package com.semashko.login.data.api

import com.google.gson.Gson
import com.semashko.login.common.url
import com.semashko.login.data.entities.LoginResponse
import com.semashko.login.data.entities.User
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody

class FirebaseAuth {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun userLogin(user: User): LoginResponse? {
        val jsonString = gson.toJson(user).toString()
        val body =
            RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonString
            )

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        val response = client.newCall(request).execute()

        return if (response.isSuccessful) {
            gson.fromJson(
                response.body().string(),
                LoginResponse::class.java
            ) ?: null
        } else {
            null
        }
    }
}