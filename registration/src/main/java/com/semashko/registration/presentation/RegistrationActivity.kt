package com.semashko.registration.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.semashko.extensions.gone
import com.semashko.extensions.visible
import com.semashko.registration.R
import com.semashko.registration.data.entities.User
import kotlinx.android.synthetic.main.activity_registration.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class RegistrationActivity : AppCompatActivity() {

    private val activityScope =
        getKoin().getOrCreateScope(
            named<RegistrationActivity>().toString(),
            named<RegistrationActivity>()
        )
    private val viewModel: RegistrationViewModel by viewModel {
        parametersOf(currentScope.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        viewModel.registrationData.observe(this, Observer {
            when (it) {
                is RegistrationUiState.Loading -> {
                    progressBar.visible()
                    Log.i("TAG", it.toString())
                }
                is RegistrationUiState.Success -> progressBar.gone()
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

    override fun onDestroy() {
        super.onDestroy()

        activityScope.close()
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
