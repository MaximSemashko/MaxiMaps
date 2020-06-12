package com.semashko.maximaps.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import java.io.IOException

private const val PICK_IMAGE_REQUEST = 22

class RegistrationActivity : AppCompatActivity() {

    private val viewModel: RegistrationViewModel by lifecycleScope.viewModel(this)

    private val userInfoPreferences: IUserInfoPreferences by inject()
    private val navigation: INavigation by inject()

    private lateinit var filePath: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

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
