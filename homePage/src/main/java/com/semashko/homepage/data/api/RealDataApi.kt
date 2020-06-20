package com.semashko.homepage.data.api

import com.google.gson.Gson
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import java.io.IOException

class RealDataApi {
    private val client = OkHttpClient()
    private val gson = Gson()

    @Throws(IOException::class)
    fun addTouristRoute(touristRoute: TouristsRoutes): Boolean {
        val jsonString = gson.toJson(touristRoute).toString()
        val body =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/TouristsRoutes.json")
            .post(body)
            .build()

        val response = client.newCall(request).execute()

        return response.isSuccessful
    }

    @Throws(IOException::class)
    fun addMansion(mansions: Mansions): Boolean {
        val jsonString = gson.toJson(mansions).toString()
        val body =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/Mansions.json")
            .post(body)
            .build()

        val response = client.newCall(request).execute()

        return response.isSuccessful
    }

    @Throws(IOException::class)
    fun addAttractions(attractions: Attractions): Boolean {
        val jsonString = gson.toJson(attractions).toString()
        val body =
            RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString)
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/Attractions.json")
            .post(body)
            .build()

        val response = client.newCall(request).execute()

        return response.isSuccessful
    }
}