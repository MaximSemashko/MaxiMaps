package com.semashko.searchfragment.data.services

import com.semashko.provider.models.home.HomeModel
import com.semashko.searchfragment.data.entitites.SearchModel
import com.semashko.searchfragment.data.entitites.SearchRequestParams

interface ISearchService {

    fun getSearchResult(requestParams: SearchRequestParams): SearchModel?
}