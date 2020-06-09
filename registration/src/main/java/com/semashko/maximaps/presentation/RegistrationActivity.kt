package com.semashko.maximaps.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.semashko.extensions.gone
import com.semashko.extensions.visible
import com.semashko.maximaps.R
import com.semashko.maximaps.data.entities.User
import com.semashko.provider.preferences.IUserInfoPreferences
import kotlinx.android.synthetic.main.activity_registration.*
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel
import java.io.IOException

private const val PICK_IMAGE_REQUEST = 22

class RegistrationActivity : AppCompatActivity() {

    private val viewModel: RegistrationViewModel by lifecycleScope.viewModel(this)

    private val userInfoPreferences: IUserInfoPreferences by inject()

    private lateinit var filePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        viewModel.registrationData.observe(this, Observer {
            when (it) {
                is RegistrationUiState.Loading -> progressBar.visible()
                is RegistrationUiState.Success -> {
                    userInfoPreferences.localId = it.result.localId
                    userInfoPreferences.token = it.result.token

                    progressBar.gone()
                }
                is RegistrationUiState.Error -> progressBar.gone()
            }
        })

        signUpButton.setOnClickListener {
            viewModel.signUp(initUserModel(), filePath)
        }

        selectImageButton.setOnClickListener {
            selectImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {

            filePath = data.data!!

            try {
                val bitmap = MediaStore.Images.Media
                    .getBitmap(
                        contentResolver,
                        filePath
                    )
                profileImageView.setImageBitmap(bitmap)
            } catch (e: IOException) {

                e.printStackTrace()
            }
        }
    }

    private fun initUserModel(): User {
        return User(
            name = nameTextInputLayout.editText?.text.toString(),
            email = emailTextInputLayout.editText?.text.toString(),
            password = passwordTextInputLayout.editText?.text.toString(),
            phone = phoneTextInputLayout.editText?.text.toString(),
            address = addressTextInputLayout.editText?.text.toString(),
            birthDay = birthdayTextInputLayout.editText?.text.toString()
        )
    }

    private fun selectImage() {
        with(Intent()) {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT

            startActivityForResult(
                Intent.createChooser(
                    this,
                    "Select Image from here..."
                ),
                PICK_IMAGE_REQUEST
            )
        }
    }

    companion object {
        fun startActivity(context: Context) {
            with(context) {
                val intent = Intent(this, RegistrationActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
