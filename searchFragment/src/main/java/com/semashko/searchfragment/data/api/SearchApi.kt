package com.semashko.searchfragment.data.api

import com.google.gson.Gson
import com.semashko.searchfragment.data.entitites.SearchItem
import com.semashko.searchfragment.data.entitites.SearchModel
import com.semashko.searchfragment.data.entitites.SearchRequestParams
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import org.json.JSONObject

class SearchApi {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun getSearchResult(requestParams: SearchRequestParams): SearchModel? {
        val request = Request.Builder()
            .url("https://maximaps.firebaseio.com/TouristsRoutes.json?orderBy=\"name\"&startAt=\"${requestParams.searchText}\"")
            .get()
            .build()

        val response = client.newCall(request).execute()
        val responseString = response.body().string()

        return if (responseString == "null") {
            SearchModel()
        } else {
            val searchItems = mutableListOf<SearchItem>()

            val objects = JSONObject(responseString)
            val iterator = objects.keys()

            while (iterator.hasNext()) {
                val route = objects.getJSONObject(iterator.next())

                searchItems.add(gson.fromJson(route.toString(), SearchItem::class.java))
            }

            SearchModel(searchItems)
        }
    }
}