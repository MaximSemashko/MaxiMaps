package com.semashko.profile.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semashko.extensions.utils.Result
import com.semashko.profile.domain.usecases.IProfileUseCase
import com.semashko.profile.presentation.ProfileUiState
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(
    private val profileUseCase: IProfileUseCase
) : ViewModel() {

    private val coroutineScopeJob = Job()
    private val profileUiMutableState = MutableLiveData<ProfileUiState>()

    val profileData: LiveData<ProfileUiState>
        get() = profileUiMutableState

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    fun loadModel() {
        coroutineContext.cancelChildren()

        profileUiMutableState.value =
            ProfileUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = profileUseCase.getUserProfile()) {
                is Result.Success -> {
                    Log.i("USER", result.value.toString())
                    profileUiMutableState.value =
                        ProfileUiState.Success(
                            result.value
                        )
                }
                is Result.Error -> ProfileUiState.Error(
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