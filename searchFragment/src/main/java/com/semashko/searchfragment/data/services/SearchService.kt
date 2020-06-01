package com.semashko.searchfragment.data.services

import com.semashko.searchfragment.data.api.SearchApi
import com.semashko.searchfragment.data.entitites.SearchRequestParams
import org.koin.core.KoinComponent
import org.koin.core.inject

class SearchService : ISearchService, KoinComponent {

    private val api: SearchApi by inject()

    override fun getSearchResult(requestParams: SearchRequestParams) = api.getSearchResult(requestParams)
}