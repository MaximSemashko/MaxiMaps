package com.semashko.seealldetailspage.koin

import com.semashko.seealldetailspage.data.api.SeeAllDataApi
import com.semashko.seealldetailspage.data.services.ISeeAllService
import com.semashko.seealldetailspage.data.services.SeeAllService
import com.semashko.seealldetailspage.domain.repositories.ISeeAllRepository
import com.semashko.seealldetailspage.domain.repositories.SeeAllRepository
import com.semashko.seealldetailspage.domain.usecases.ISeeAllUseCase
import com.semashko.seealldetailspage.domain.usecases.SeeAllUseCase
import com.semashko.seealldetailspage.presentation.fragments.SeeAllFragment
import com.semashko.seealldetailspage.presentation.viewmodels.SeeAllViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val seeAllModule = module {
    factory {
        SeeAllDataApi()
    }

    scope(named<SeeAllFragment>()) {
        scoped<ISeeAllService> { SeeAllService() }
        scoped<ISeeAllRepository> {
            SeeAllRepository(
                seeAllService = get()
            )
        }
        scoped<ISeeAllUseCase> {
            SeeAllUseCase(
                seeAllRepository = get()
            )
        }
        viewModel {
            SeeAllViewModel(
                seeAllUseCase = get()
            )
        }
    }
}
