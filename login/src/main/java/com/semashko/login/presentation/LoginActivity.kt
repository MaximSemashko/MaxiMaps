package com.semashko.login.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.semashko.extensions.gone
import com.semashko.extensions.visible
import com.semashko.login.R
import com.semashko.login.data.entities.User
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class LoginActivity : AppCompatActivity() {

    private val activityScope =
        getKoin().getOrCreateScope(named<LoginActivity>().toString(), named<LoginActivity>())
    private val viewModel: LoginViewModel by viewModel {
        parametersOf(currentScope.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel.loginData.observe(this, Observer {
            when (it) {
                is LoginUiState.Loading -> {
                    progressBar.visible()
                    Log.i("TAG", it.toString())
                }
                is LoginUiState.Success -> progressBar.gone()
                is LoginUiState.Error -> progressBar.gone()
            }
        })

        loginButton.setOnClickListener {
            val email = emailTextInputLayout.editText?.text.toString()
            val password = passwordTextInputLayout.editText?.text.toString()

            viewModel.load(User("max1@mail.ru", "123123"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        activityScope.close()
    }

    companion object {
        fun startActivity(context: Context) {
            with(context) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
