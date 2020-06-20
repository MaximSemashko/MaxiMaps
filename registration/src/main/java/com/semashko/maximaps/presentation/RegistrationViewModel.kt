package com.semashko.maximaps.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.semashko.extensions.constants.EMPTY
import com.semashko.extensions.utils.Result
import com.semashko.maximaps.data.entities.User
import com.semashko.maximaps.domain.usecases.ISignUpUseCase
import com.semashko.provider.preferences.IUserInfoPreferences
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.coroutines.CoroutineContext


class RegistrationViewModel(
    private val signUpUseCase: ISignUpUseCase
) : ViewModel(), KoinComponent {

    private val userInfoPreferences: IUserInfoPreferences by inject()
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
                    userInfoPreferences.name = user.name ?: EMPTY
                    signUpUseCase.addUserToRealtimeDatabase(
                        user = user.copy(
                            localId = result.value.localId,
                            imageUrl = "https://firebasestorage.googleapis.com/v0/b/maximaps.appspot.com/o/${result.value.localId}?alt=media"
                        ),
                        localId = result.value.localId
                    )
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
            .child(id)

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