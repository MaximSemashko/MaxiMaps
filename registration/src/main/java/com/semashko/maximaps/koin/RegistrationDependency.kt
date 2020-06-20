package com.semashko.maximaps.koin

import com.semashko.maximaps.data.api.FirebaseRegistration
import com.semashko.maximaps.data.services.IRegistrationService
import com.semashko.maximaps.data.services.RegistrationService
import com.semashko.maximaps.domain.repositories.ISignUpRepository
import com.semashko.maximaps.domain.repositories.SignUpRepository
import com.semashko.maximaps.domain.usecases.ISignUpUseCase
import com.semashko.maximaps.domain.usecases.SignUpUseCase
import com.semashko.maximaps.presentation.RegistrationActivity
import com.semashko.maximaps.presentation.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val registrationModule = module {
    factory {
        FirebaseRegistration()
    }

    scope(named<RegistrationActivity>()) {
        scoped<IRegistrationService> { RegistrationService() }
        scoped<ISignUpRepository> {
            SignUpRepository(
                registrationService = get()
            )
        }
        scoped<ISignUpUseCase> {
            SignUpUseCase(
                signUpRepository = get()
            )
        }
        viewModel {
            RegistrationViewModel(
                signUpUseCase = get()
            )
        }
    }
}