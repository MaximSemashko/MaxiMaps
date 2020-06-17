package com.semashko.comments.data.api

import com.google.gson.Gson
import com.semashko.comments.data.entities.Reviews
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import org.json.JSONObject
import java.io.IOException

class CommentsApi {
    private val client = OkHttpClient()
    private val gson = Gson()
    private val localId = "123"

    fun getComments(): List<Reviews>? {
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/${localId}/Reviews.json")
            .get()
            .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        return if (responseString == "null") {
            emptyList()
        } else {
            val comments = mutableListOf<Reviews>()

            val objects = JSONObject(responseString)
            val iterator = objects.keys()

            while (iterator.hasNext()) {
                val bookmark = objects.getJSONObject(iterator.next())

                comments.add(gson.fromJson(bookmark.toString(), Reviews::class.java))
            }

            comments
        }
    }

    @Throws(IOException::class)
    fun addComment(review: Reviews): Boolean {
        val jsonString = Gson().toJson(review).toString()
        val body =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/${localId}/Reviews.json")
            .post(body)
            .build()

        val response = OkHttpClient().newCall(request).execute()

        return response.isSuccessful
    }
}