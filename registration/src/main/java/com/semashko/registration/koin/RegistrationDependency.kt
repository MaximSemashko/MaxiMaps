package com.semashko.registration.koin

import com.semashko.registration.data.api.FirebaseRegistration
import com.semashko.registration.data.services.IRegistrationService
import com.semashko.registration.data.services.RegistrationService
import com.semashko.registration.domain.repositories.ISignUpRepository
import com.semashko.registration.domain.repositories.SignUpRepository
import com.semashko.registration.domain.usecases.ISignUpUseCase
import com.semashko.registration.domain.usecases.SignUpUseCase
import com.semashko.registration.presentation.RegistrationActivity
import com.semashko.registration.presentation.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.ScopeID
import org.koin.dsl.module

object RegistrationDependency {
    val module = module {
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
        }

        viewModel { (scopeId: ScopeID) ->
            RegistrationViewModel(
                signUpUseCase = getScope(scopeId).get()
            )
        }
    }
}