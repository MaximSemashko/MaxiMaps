package com.semashko.homepage.domain.usecases

import com.semashko.extensions.utils.Result
import com.semashko.provider.models.home.HomeModel

interface IHomeUseCase {

    suspend fun getHomeModel(): Result<HomeModel>
}