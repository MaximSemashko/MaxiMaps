package com.semashko.profile.koin

import com.semashko.profile.data.api.ProfileDataApi
import com.semashko.profile.data.services.ProfileService
import com.semashko.profile.data.services.IProfileService
import com.semashko.profile.domain.repositories.ProfileRepository
import com.semashko.profile.domain.repositories.IProfileRepository
import com.semashko.profile.domain.usecases.ProfileUseCase
import com.semashko.profile.domain.usecases.IProfileUseCase
import com.semashko.profile.presentation.viewmodels.ProfileViewModel
import com.semashko.profile.presentation.fragments.ProfileFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val profileModule = module {
    factory {
        ProfileDataApi()
    }

    scope(named<ProfileFragment>()) {
        scoped<IProfileService> { ProfileService() }
        scoped<IProfileRepository> {
            ProfileRepository(
                profileService = get()
            )
        }
        scoped<IProfileUseCase> {
            ProfileUseCase(
                profileRepository = get()
            )
        }
        viewModel {
            ProfileViewModel(
                profileUseCase = get()
            )
        }
    }
}
