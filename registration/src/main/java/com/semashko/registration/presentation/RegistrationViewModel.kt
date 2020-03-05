package com.semashko.registration.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semashko.extensions.utils.Result
import com.semashko.registration.data.entities.User
import com.semashko.registration.domain.usecases.ISignUpUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RegistrationViewModel(
    private val signUpUseCase: ISignUpUseCase
) : ViewModel() {

    private val coroutineScopeJob = Job()
    private val registrationUiMutableState = MutableLiveData<RegistrationUiState>()

    val registrationData: LiveData<RegistrationUiState>
        get() = registrationUiMutableState

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun signUp(user: User) {
        coroutineContext.cancelChildren()

        registrationUiMutableState.value = RegistrationUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = signUpUseCase.
                signUp(user)) {
                is Result.Success -> {
                    registrationUiMutableState.value = RegistrationUiState.Success(result.value)
                    Log.i("TAG", result.value.toString())
                }
                is Result.Error -> RegistrationUiState.Error(result.exception)
            }
        }
    }
    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}