package com.semashko.dialogs.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semashko.dialogs.domain.usecases.IDialogUseCase
import com.semashko.extensions.utils.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DialogViewModel(
    private val dialgUseCase: IDialogUseCase
) : ViewModel() {

    private val coroutineScopeJob = Job()
    private val dialogUiMutableState = MutableLiveData<DialogUiState>()

    val dialogData: LiveData<DialogUiState>
        get() = dialogUiMutableState

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun load() {
        coroutineContext.cancelChildren()

        dialogUiMutableState.value = DialogUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = dialgUseCase.getDialogs()) {
                is Result.Success -> {
                    dialogUiMutableState.value = DialogUiState.Success(result.value)
                    Log.i("DIALOGS", result.value.toString())
                }
                is Result.Error -> DialogUiState.Error(result.exception)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}