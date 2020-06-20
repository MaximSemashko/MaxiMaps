package com.semashko.searchfragment.domain.repositories

import com.semashko.extensions.utils.Result
import com.semashko.searchfragment.data.entitites.SearchModel
import com.semashko.searchfragment.data.entitites.SearchRequestParams
import com.semashko.searchfragment.data.services.ISearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(
    private val searchService: ISearchService
) : ISearchRepository {
    override suspend fun getSearchResult(params: SearchRequestParams): Result<SearchModel> {
        return withContext(Dispatchers.IO) {
            runCatching {
                Result.Success(searchService.getSearchResult(params) ?: SearchModel())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }
}