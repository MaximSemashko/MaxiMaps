package com.semashko.searchfragment.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.searchfragment.data.entitites.SearchModel
import com.semashko.searchfragment.data.entitites.SearchRequestParams

interface ISearchRepository {

    suspend fun getSearchResult(params: SearchRequestParams): Result<SearchModel>
}