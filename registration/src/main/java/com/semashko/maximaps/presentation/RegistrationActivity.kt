package com.semashko.maximaps.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.semashko.extensions.action
import com.semashko.extensions.constants.EMPTY
import com.semashko.extensions.gone
import com.semashko.extensions.snack
import com.semashko.extensions.visible
import com.semashko.maximaps.R
import com.semashko.maximaps.data.entities.User
import com.semashko.provider.navigation.INavigation
import com.semashko.provider.preferences.IUserInfoPreferences
import kotlinx.android.synthetic.main.activity_registration.*
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

private const val PICK_IMAGE_REQUEST = 22

class RegistrationActivity : AppCompatActivity() {

    private val viewModel: RegistrationViewModel by lifecycleScope.viewModel(this)

    private val userInfoPreferences: IUserInfoPreferences by inject()
    private val navigation: INavigation by inject()

    private lateinit var filePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        viewModel.registrationData.observe(this, Observer {
            when (it) {
                is RegistrationUiState.Loading -> progressBar.visible()
                is RegistrationUiState.Success -> {
                    userInfoPreferences.localId = it.result.localId ?: EMPTY
                    userInfoPreferences.token = it.result.token ?: EMPTY

                    if (!it.result.localId.isNullOrEmpty() && !it.result.token.isNullOrEmpty()) {
                        navigation.openMainActivity()
                    }

                    progressBar.gone()
                }
                is RegistrationUiState.Error -> progressBar.gone()
            }
        })

        signUpButton.setOnClickListener { view ->
            initUserModel(view)?.let { user -> viewModel.signUp(user, filePath) }
        }

        selectImageButton.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val gallery =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST) {
            filePath = data?.data ?: Uri.EMPTY
            profileImageView.setImageURI(filePath)
        }
    }

    private fun initUserModel(view: View): User? {
        val name = nameTextInputLayout.editText?.text.toString()
        val email = emailTextInputLayout.editText?.text.toString()
        val password = passwordTextInputLayout.editText?.text.toString()
        val phone = phoneTextInputLayout.editText?.text.toString()
        val address = addressTextInputLayout.editText?.text.toString()
        val birthDay = birthdayTextInputLayout.editText?.text.toString()

        return if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            User(
                name = name,
                email = email,
                password = password,
                phone = phone,
                address = address,
                birthDay = birthDay
            )
        } else {
            view.snack("Check your email, name and password!") {
                action("Close") {}
            }

            null
        }
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
