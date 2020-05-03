package com.semashko.seealldetailspage.data.api

import com.google.gson.Gson
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.json.JSONObject

class SeeAllDataApi {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun getTouristsRoutes(): List<TouristsRoutes>? {
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

    fun getMansions(): List<Mansions>? {
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/Mansions.json")
            .get()
            .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        return if (responseString == "null") {
            emptyList()
        } else {
            val mansions = mutableListOf<Mansions>()

            val objects = JSONObject(responseString)
            val iterator = objects.keys()

            while (iterator.hasNext()) {
                val route = objects.getJSONObject(iterator.next())

                mansions.add(gson.fromJson(route.toString(), Mansions::class.java))
            }

            mansions
        }
    }

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