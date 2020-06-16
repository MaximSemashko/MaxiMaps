package com.semashko.itemdetailspage.data.api

import com.google.gson.Gson
import com.semashko.provider.models.detailsPage.ItemDetails
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.preferences.IUserInfoPreferences
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import org.json.JSONObject
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.IOException

class RecommendationsDataApi : KoinComponent {
    private val userInfoPreferences: IUserInfoPreferences by inject()

    private val client = OkHttpClient()
    private val gson = Gson()

    fun getAttractions(): List<Attractions>? {
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/Attractions.json")
            .get()
            .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        return if (responseString == "null") {
            emptyList()
        } else {
            val attractions = mutableListOf<Attractions>()

            val objects = JSONObject(responseString)
            val iterator = objects.keys()

            while (iterator.hasNext()) {
                val route = objects.getJSONObject(iterator.next())

                attractions.add(gson.fromJson(route.toString(), Attractions::class.java))
            }

            attractions
        }
    }

    @Throws(IOException::class)
    fun addItemToBookmarks(itemDetails: ItemDetails): Boolean {
        val jsonString = Gson().toJson(itemDetails).toString()
        val body =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/${userInfoPreferences.localId}/Bookmarks.json")
            .post(body)
            .build()

        val response = OkHttpClient().newCall(request).execute()

        return response.isSuccessful
    }
}