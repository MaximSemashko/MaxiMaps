package com.semashko.seealldetailspage.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semashko.extensions.utils.Result
import com.semashko.seealldetailspage.domain.usecases.ISeeAllUseCase
import com.semashko.seealldetailspage.presentation.AttractionsUiState
import com.semashko.seealldetailspage.presentation.MansionsUiState
import com.semashko.seealldetailspage.presentation.RoutesUiState
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SeeAllViewModel(
    private val seeAllUseCase: ISeeAllUseCase
) : ViewModel() {

    private val coroutineScopeJob = Job()

    private val mansionsUiMutableState = MutableLiveData<MansionsUiState>()
    private val routesUiMutableState = MutableLiveData<RoutesUiState>()
    private val attractionsUiMutableState = MutableLiveData<AttractionsUiState>()

    val mansionsData: LiveData<MansionsUiState>
        get() = mansionsUiMutableState

    val routesData: LiveData<RoutesUiState>
        get() = routesUiMutableState

    val attractionsData: LiveData<AttractionsUiState>
        get() = attractionsUiMutableState

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    fun loadMansions() {
        coroutineContext.cancelChildren()

        mansionsUiMutableState.value =
            MansionsUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = seeAllUseCase.getMansions()) {
                is Result.Success -> {
                    Log.i("MANSIONS", result.value.toString())
                    mansionsUiMutableState.value = MansionsUiState.Success(result.value)
                }
                is Result.Error -> MansionsUiState.Error(result.exception)
            }
        }
    }

    fun loadRoutes() {
        coroutineContext.cancelChildren()

        routesUiMutableState.value =
            RoutesUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = seeAllUseCase.getRoutes()) {
                is Result.Success -> {
                    Log.i("ROUTES", result.value.toString())
                    routesUiMutableState.value =
                        RoutesUiState.Success(result.value)
                }
                is Result.Error -> RoutesUiState.Error(result.exception)
            }
        }
    }

    fun loadAttractions() {
        coroutineContext.cancelChildren()

        attractionsUiMutableState.value =
            AttractionsUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = seeAllUseCase.getAttractions()) {
                is Result.Success -> {
                    Log.i("ATTRACTIONS", result.value.toString())
                    attractionsUiMutableState.value =
                        AttractionsUiState.Success(result.value)
                }
                is Result.Error -> AttractionsUiState.Error(result.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}