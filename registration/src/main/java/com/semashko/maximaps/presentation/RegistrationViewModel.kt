package com.semashko.maximaps.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.semashko.extensions.utils.Result
import com.semashko.maximaps.data.entities.User
import com.semashko.maximaps.domain.usecases.ISignUpUseCase
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

    fun signUp(user: User, filePath: Uri) {
        coroutineContext.cancelChildren()

        registrationUiMutableState.value = RegistrationUiState.Loading

        CoroutineScope(coroutineContext).launch {
            when (val result = signUpUseCase.signUp(user)) {
                is Result.Success -> {
                    signUpUseCase.addUserToRealtimeDatabase(user, result.value.localId)
                    user.email?.let {
                        result.value.localId?.let { it1 ->
                            uploadImage(
                                filePath,
                                it1
                            )
                        }
                    }
                    registrationUiMutableState.value = RegistrationUiState.Success(result.value)
                    Log.i("TAG", result.value.toString())
                }
                is Result.Error -> RegistrationUiState.Error(result.exception)
            }
        }
    }

    private fun uploadImage(filePath: Uri, id: String) {
        val storageReference: StorageReference = FirebaseStorage.getInstance().reference
            .child("images/" + id)

        storageReference.putFile(filePath)
            .addOnSuccessListener {}
            .addOnFailureListener { }
            .addOnProgressListener { }
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}