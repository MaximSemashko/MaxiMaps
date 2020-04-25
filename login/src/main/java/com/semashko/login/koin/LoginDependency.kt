package com.semashko.login.koin

import com.semashko.login.data.api.FirebaseAuth
import com.semashko.login.domain.repositories.ILoginRepository
import com.semashko.login.domain.repositories.LoginRepository
import com.semashko.login.data.services.ILoginService
import com.semashko.login.data.services.LoginService
import com.semashko.login.domain.usecases.ILoginUseCase
import com.semashko.login.domain.usecases.LoginUseCase
import com.semashko.login.presentation.LoginActivity
import com.semashko.login.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.ScopeID
import org.koin.dsl.module

val loginModule = module {
    factory {
        FirebaseAuth()
    }

    scope(named<LoginActivity>()) {
        scoped<ILoginService> { LoginService() }
        scoped<ILoginRepository> {
            LoginRepository(
                loginService = get()
            )
        }
        scoped<ILoginUseCase> {
            LoginUseCase(
                loginRepository = get()
            )
        }
        viewModel {
            LoginViewModel(
                loginUseCase = get()
            )
        }
    }
}
