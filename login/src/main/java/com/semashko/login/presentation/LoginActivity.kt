package com.semashko.login.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.semashko.extensions.action
import com.semashko.extensions.constants.EMPTY
import com.semashko.extensions.gone
import com.semashko.extensions.snack
import com.semashko.extensions.visible
import com.semashko.login.R
import com.semashko.login.data.entities.User
import com.semashko.provider.navigation.INavigation
import com.semashko.provider.preferences.IUserInfoPreferences
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by lifecycleScope.viewModel(this)

    private val userInfoPreferences: IUserInfoPreferences by inject()
    private val navigation: INavigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.loginData.observe(this, Observer {
            when (it) {
                is LoginUiState.Loading -> progressBar.visible()
                is LoginUiState.Success -> {
                    userInfoPreferences.localId = it.result.localId ?: EMPTY
                    userInfoPreferences.token = it.result.token ?: EMPTY

                    if (!it.result.localId.isNullOrEmpty() && !it.result.token.isNullOrEmpty()) {
                        navigation.openMainActivity()
                    }

                    progressBar.gone()
                }
                is LoginUiState.Error -> progressBar.gone()
            }
        })

        loginButton.setOnClickListener {
            prepareUser(it)?.let { user -> viewModel.load(user) }
        }

        registrationButton.setOnClickListener {
            navigation.openRegistrationActivity()
        }
    }

    private fun prepareUser(view: View): User? {
        val email = emailTextInputLayout.editText?.text.toString()
        val password = passwordTextInputLayout.editText?.text.toString()

        return if (email.isNotEmpty() && password.isNotEmpty()) {
            User(email, password)
        } else {
            view.snack("Check your email or password") {
                action("Close") {}
            }

            null
        }
    }

    companion object {
        fun startActivity(context: Context) {
            with(context) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
            }
        }
    }
}
