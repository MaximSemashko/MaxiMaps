package com.semashko.itemdetailspage.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semashko.extensions.utils.Result
import com.semashko.itemdetailspage.domain.usecases.IRecommendationsUseCase
import com.semashko.itemdetailspage.presentation.ItemUiState
import com.semashko.itemdetailspage.presentation.RecommendationsUiState
import com.semashko.provider.models.detailsPage.ItemDetails
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RecommendationsViewModel(
    private val recommendationsUseCase: IRecommendationsUseCase
) : ViewModel() {

    private val coroutineScopeJob = Job()

    private val recommendationsUiMutableState = MutableLiveData<RecommendationsUiState>()
    private val itemUiMutableState = MutableLiveData<ItemUiState>()

    val recommendationsData: LiveData<RecommendationsUiState>
        get() = recommendationsUiMutableState

    val itemData: LiveData<ItemUiState>
        get() = itemUiMutableState

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    fun loadRecommendations() {
        coroutineContext.cancelChildren()

        recommendationsUiMutableState.value =
            RecommendationsUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = recommendationsUseCase.getRecommendations()) {
                is Result.Success -> {
                    Log.i("recommendations", result.value.toString())
                    recommendationsUiMutableState.value =
                        RecommendationsUiState.Success(result.value)
                }
                is Result.Error -> RecommendationsUiState.Error(result.exception)
            }
        }
    }

    fun addItemToBookmarks(itemDetails: ItemDetails) {
        coroutineContext.cancelChildren()

        itemUiMutableState.value = ItemUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = recommendationsUseCase.addItemToBookmarks(itemDetails)) {
                is Result.Success -> itemUiMutableState.value = ItemUiState.Success(result.value)
                is Result.Error -> ItemUiState.Error(result.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}