package com.semashko.users.koin

import com.semashko.users.data.api.UsersApi
import com.semashko.users.data.services.IUsersService
import com.semashko.users.data.services.UsersService
import com.semashko.users.domain.repositories.IUsersRepository
import com.semashko.users.domain.repositories.UsersRepository
import com.semashko.users.domain.usecases.IUsersUseCase
import com.semashko.users.domain.usecases.UsersUseCase
import com.semashko.users.presentation.UsersViewModel
import com.semashko.users.presentation.fragments.UsersFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val usersModule = module {
    factory {
        UsersApi()
    }

    scope(named<UsersFragment>()) {
        scoped<IUsersService> { UsersService() }
        scoped<IUsersRepository> {
            UsersRepository(
                service = get()
            )
        }
        scoped<IUsersUseCase> {
            UsersUseCase(
                repository = get()
            )
        }
        viewModel {
            UsersViewModel(
                usersUseCase = get()
            )
        }
    }
}
