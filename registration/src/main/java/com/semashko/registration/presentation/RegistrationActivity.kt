package com.semashko.registration.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.semashko.extensions.gone
import com.semashko.extensions.visible
import com.semashko.provider.preferences.IUserInfoPreferences
import com.semashko.registration.R
import com.semashko.registration.data.entities.User
import kotlinx.android.synthetic.main.activity_registration.*
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class RegistrationActivity : AppCompatActivity() {

    private val viewModel: RegistrationViewModel by lifecycleScope.viewModel(this)
    private val userInfoPreferences: IUserInfoPreferences by inject()

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
            viewModel.signUp(
                User(
                    name = "Maxim",
                    email = "maxim123@email.com",
                    gender = "male",
                    password = "123123"
                )
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
