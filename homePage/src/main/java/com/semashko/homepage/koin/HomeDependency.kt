package com.semashko.homepage.koin

import com.semashko.homepage.data.api.HomeDataApi
import com.semashko.homepage.data.services.HomeService
import com.semashko.homepage.data.services.IHomeService
import com.semashko.homepage.domain.repositories.HomeRepository
import com.semashko.homepage.domain.repositories.IHomeRepository
import com.semashko.homepage.domain.usecases.HomeUseCase
import com.semashko.homepage.domain.usecases.IHomeUseCase
import com.semashko.homepage.presentation.HomeFragment
import com.semashko.homepage.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.ScopeID
import org.koin.dsl.module

val homeModule = module {
    factory {
        HomeDataApi()
    }

    scope(named<HomeFragment>()) {
        scoped<IHomeService> { HomeService() }
        scoped<IHomeRepository> {
            HomeRepository(
                homeService = get()
            )
        }
        scoped<IHomeUseCase> {
            HomeUseCase(
                homeRepository = get()
            )
        }
        viewModel {
            HomeViewModel(
                homeUseCase = get()
            )
        }
    }
}
