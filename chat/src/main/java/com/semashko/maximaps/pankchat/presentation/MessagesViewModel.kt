package com.semashko.maximaps.pankchat.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.semashko.extensions.utils.Result
import com.semashko.maximaps.pankchat.domain.usecases.IChatUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MessagesViewModel(
    private val chatUseCase: IChatUseCase
) : ViewModel() {

    private val coroutineScopeJob = Job()
    private val messagesUiMutableState = MutableLiveData<MessagesUiState>()
    private val itemUiMutableState = MutableLiveData<ItemUiState>()

    val messagesData: LiveData<MessagesUiState>
        get() = messagesUiMutableState

    val itemData: LiveData<ItemUiState>
        get() = itemUiMutableState

    private val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun loadMessages() {
        coroutineContext.cancelChildren()

        messagesUiMutableState.value = MessagesUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = chatUseCase.getMessages()) {
                is Result.Success -> {
                    messagesUiMutableState.value = MessagesUiState.Success(result.value)
                    Log.i("TAG", result.value.toString())
                }
                is Result.Error -> MessagesUiState.Error(result.exception)
            }
        }
    }

    fun sendMessage(
        message: com.semashko.maximaps.pankchat.data.entities.Message,
        localId: String
    ) {
        coroutineContext.cancelChildren()

        itemUiMutableState.value = ItemUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = chatUseCase.sendMessage(message, localId)) {
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