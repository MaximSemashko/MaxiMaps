package com.semashko.itemdetailspage.data.api

import com.google.gson.Gson
import com.semashko.provider.models.home.Attractions
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.json.JSONObject

class RecommendationsDataApi {
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
}