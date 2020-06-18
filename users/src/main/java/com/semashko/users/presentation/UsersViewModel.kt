package com.semashko.users.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semashko.extensions.utils.Result
import com.semashko.users.domain.usecases.IUsersUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UsersViewModel(
    private val usersUseCase: IUsersUseCase
) : ViewModel() {

    private val coroutineScopeJob = Job()
    private val usersUiMutableState = MutableLiveData<UsersUiState>()

    val usersData: LiveData<UsersUiState>
        get() = usersUiMutableState

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun load() {
        coroutineContext.cancelChildren()

        usersUiMutableState.value = UsersUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = usersUseCase.getUsers()) {
                is Result.Success -> {
                    usersUiMutableState.value = UsersUiState.Success(result.value)
                    Log.i("TAG", result.value.toString())
                }
                is Result.Error -> UsersUiState.Error(result.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}