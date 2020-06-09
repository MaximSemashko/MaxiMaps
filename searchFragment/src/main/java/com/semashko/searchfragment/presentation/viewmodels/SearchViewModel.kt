package com.semashko.searchfragment.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semashko.extensions.utils.Result
import com.semashko.searchfragment.data.entitites.SearchItem
import com.semashko.searchfragment.data.entitites.SearchModel
import com.semashko.searchfragment.data.entitites.SearchRequestParams
import com.semashko.searchfragment.domain.usecases.ISearchUseCase
import com.semashko.searchfragment.presentation.SearchUiState
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

const val FIRST_PAGE_NUMBER = 0

class SearchViewModel(private val searchUseCase: ISearchUseCase) : ViewModel(), CoroutineScope {

    private var currentParams: SearchRequestParams = SearchRequestParams()
    private var searchModel: SearchModel = SearchModel()
    private var pageNumber = FIRST_PAGE_NUMBER

    private val coroutineScopeJob = Job()
    private val searchUiMutableState = MutableLiveData<SearchUiState>()

    val searchData: LiveData<SearchUiState>
        get() = searchUiMutableState

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun load() {
        if (currentParams.pageNumber != FIRST_PAGE_NUMBER) {
            searchUiMutableState.value = SearchUiState.Success(searchModel)
        }
    }

    fun load(searchText: String) {
        if (searchText != currentParams.searchText) {
            searchUiMutableState.value = SearchUiState.NewSearch

            searchModel = SearchModel()

            pageNumber = FIRST_PAGE_NUMBER
            currentParams = currentParams.copy(
                searchText = searchText,
                pageNumber = pageNumber
            )

            loadModel()
        }
    }

    fun loadMore() {
        searchUiMutableState.value = SearchUiState.Pagination

        currentParams = currentParams.copy(pageNumber = pageNumber)

        loadModel()
    }

    private fun loadModel() {
        coroutineContext.cancelChildren()

        launch {
            when (val result = searchUseCase.getSearchModel(currentParams)) {
                is Result.Success -> {
                    searchModel = mergeResults(searchModel, result.value)

                    searchUiMutableState.value = SearchUiState.Success(searchModel)

                    pageNumber++
                }
                is Result.Error -> {
                    searchUiMutableState.value = SearchUiState.Error(result.exception)
                }
            }
        }
    }

    private fun mergeResults(
        oldResponse: SearchModel,
        newResponse: SearchModel
    ): SearchModel {
        val itemsList = mutableListOf<SearchItem>()

        itemsList += oldResponse.searchItems
        itemsList += newResponse.searchItems

        return SearchModel(itemsList)
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}