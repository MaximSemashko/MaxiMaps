package com.semashko.login.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semashko.extensions.utils.Result
import com.semashko.login.data.entities.User
import com.semashko.login.domain.usecases.ILoginUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginViewModel(
    private val loginUseCase: ILoginUseCase
) : ViewModel() {

    private val coroutineScopeJob = Job()
    private val loginUiMutableState = MutableLiveData<LoginUiState>()

    val loginData: LiveData<LoginUiState>
        get() = loginUiMutableState

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun load(user: User) {
        coroutineContext.cancelChildren()

        loginUiMutableState.value = LoginUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = loginUseCase.
                login(user)) {
                is Result.Success -> {
                    loginUiMutableState.value = LoginUiState.Success(result.value)
                    Log.i("TAG", result.value.toString())
                }
                is Result.Error -> LoginUiState.Error(result.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}