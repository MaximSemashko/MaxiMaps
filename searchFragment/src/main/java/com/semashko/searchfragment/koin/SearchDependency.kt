package com.semashko.searchfragment.koin

import com.semashko.searchfragment.data.api.SearchApi
import com.semashko.searchfragment.data.services.ISearchService
import com.semashko.searchfragment.data.services.SearchService
import com.semashko.searchfragment.domain.repositories.ISearchRepository
import com.semashko.searchfragment.domain.repositories.SearchRepository
import com.semashko.searchfragment.domain.usecases.ISearchUseCase
import com.semashko.searchfragment.domain.usecases.SearchUseCase
import com.semashko.searchfragment.presentation.fragments.SearchFragment
import com.semashko.searchfragment.presentation.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val searchModule = module {
    factory {
        SearchApi()
    }

    scope(named<SearchFragment>()) {
        scoped<ISearchService> { SearchService() }
        scoped<ISearchRepository> {
            SearchRepository(
                searchService = get()
            )
        }
        scoped<ISearchUseCase> {
            SearchUseCase(
                searchRepository = get()
            )
        }
        viewModel {
            SearchViewModel(
                searchUseCase = get()
            )
        }
    }
}
