package com.semashko.searchfragment.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.searchfragment.data.entitites.SearchModel
import com.semashko.searchfragment.data.entitites.SearchRequestParams

interface ISearchUseCase {

    suspend fun getSearchModel(requestParams: SearchRequestParams): Result<SearchModel>
}