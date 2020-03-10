package com.semashko.homepage.data.api

import com.google.gson.Gson
import com.semashko.homepage.data.entities.TouristsRoutes
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.json.JSONObject

class HomeDataApi {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun getTouristsRoutes(): List<TouristsRoutes> {
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/TouristsRoutes.json")
            .get()
            .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        return if (responseString == "null") {
            emptyList()
        } else {
            val touristsRoutes = mutableListOf<TouristsRoutes>()

            val objects = JSONObject(responseString)
            val iterator = objects.keys()

            while (iterator.hasNext()) {
                val route = objects.getJSONObject(iterator.next())

                touristsRoutes.add(gson.fromJson(route.toString(), TouristsRoutes::class.java))
            }

            touristsRoutes
        }
    }
}