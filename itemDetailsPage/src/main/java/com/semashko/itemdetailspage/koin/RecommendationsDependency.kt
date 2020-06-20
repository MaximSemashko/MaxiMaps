package com.semashko.itemdetailspage.koin

import com.semashko.itemdetailspage.data.api.RecommendationsDataApi
import com.semashko.itemdetailspage.data.services.IRecommendationsService
import com.semashko.itemdetailspage.data.services.RecommendationsService
import com.semashko.itemdetailspage.domain.repositories.IRecommendationsRepository
import com.semashko.itemdetailspage.domain.repositories.RecommendationsRepository
import com.semashko.itemdetailspage.domain.usecases.IRecommendationsUseCase
import com.semashko.itemdetailspage.domain.usecases.RecommendationsUseCase
import com.semashko.itemdetailspage.presentation.fragments.ItemDetailsPageFragment
import com.semashko.itemdetailspage.presentation.viewmodels.RecommendationsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val recommendationsModule = module {
    factory {
        RecommendationsDataApi()
    }

    scope(named<ItemDetailsPageFragment>()) {
        scoped<IRecommendationsService> { RecommendationsService() }
        scoped<IRecommendationsRepository> {
            RecommendationsRepository(
                recommendationsService = get()
            )
        }
        scoped<IRecommendationsUseCase> {
            RecommendationsUseCase(
                recommendationsRepository = get()
            )
        }
        viewModel {
            RecommendationsViewModel(
                recommendationsUseCase = get()
            )
        }
    }
}
