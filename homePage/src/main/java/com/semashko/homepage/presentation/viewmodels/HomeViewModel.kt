package com.semashko.homepage.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semashko.extensions.utils.Result
import com.semashko.homepage.domain.usecases.IHomeUseCase
import com.semashko.homepage.presentation.HomeUiState
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeViewModel(
    private val homeUseCase: IHomeUseCase
) : ViewModel() {

    private val coroutineScopeJob = Job()
    private val homeUiMutableState = MutableLiveData<HomeUiState>()

    val homeData: LiveData<HomeUiState>
        get() = homeUiMutableState

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    fun loadModel() {
        coroutineContext.cancelChildren()

        homeUiMutableState.value =
            HomeUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = homeUseCase.getHomeModel()) {
                is Result.Success -> {
                    Log.i("ATTRACTIONS", result.value.attractions.toString())
                    Log.i("ROUTES", result.value.routes.toString())
                    Log.i("MANSIONS", result.value.mansions.toString())
                    homeUiMutableState.value =
                        HomeUiState.Success(
                            result.value
                        )
                }
                is Result.Error -> HomeUiState.Error(
                    result.exception
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}