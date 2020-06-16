package com.semashko.profile.data.api

import com.google.gson.Gson
import com.semashko.profile.data.entities.User
import com.semashko.provider.preferences.IUserInfoPreferences
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProfileDataApi : KoinComponent {
    private val userInfoPreferences: IUserInfoPreferences by inject()

    private val client = OkHttpClient()
    private val gson = Gson()

    fun getUserProfile(): User? {
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/users/${userInfoPreferences.localId}.json")
            .get()
            .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        return gson.fromJson(responseString, User::class.java)
    }
}