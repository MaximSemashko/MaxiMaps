package com.semashko.searchfragment.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.searchfragment.data.entitites.SearchModel
import com.semashko.searchfragment.data.entitites.SearchRequestParams
import com.semashko.searchfragment.domain.repositories.ISearchRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class SearchUseCase(
    private val searchRepository: ISearchRepository
) : ISearchUseCase {
    override suspend fun getSearchModel(requestParams: SearchRequestParams): Result<SearchModel> {
        return coroutineScope {
            val searchAsync = async { searchRepository.getSearchResult(requestParams) }

            val searchModel = when (val result = searchAsync.await()) {
                is Result.Success -> result.value
                is Result.Error -> SearchModel()
            }

            Result.Success(
                searchModel
            )
        }
    }
}