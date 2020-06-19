package com.semashko.homepage.domain.repositories

import android.util.Log
import com.google.gson.Gson
import com.semashko.dbroom.MaximapsRoomDatabase
import com.semashko.dbroom.dao.AttractionsDao
import com.semashko.dbroom.dao.MansionsDao
import com.semashko.dbroom.dao.TouristsRoutesDao
import com.semashko.extensions.utils.Result
import com.semashko.homepage.data.services.IHomeService
import com.semashko.homepage.mappers.toDbModel
import com.semashko.homepage.mappers.toModel
import com.semashko.provider.models.home.Attractions
import com.semashko.provider.models.home.Mansions
import com.semashko.provider.models.home.TouristsRoutes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

class HomeRepository(
    private val homeService: IHomeService
) : IHomeRepository, KoinComponent {

    private val gson = Gson()

    private val database: MaximapsRoomDatabase by inject()

    private val mansionsDao: MansionsDao = database.mansionsDao()
    private val touristsRoutesDao: TouristsRoutesDao = database.touristsRoutesDao()
    private val attractionsDao: AttractionsDao = database.attractionsDao()


    override suspend fun getTouristsRoutes(): Result<List<TouristsRoutes>> {
        return withContext(Dispatchers.IO) {
            runCatching {
//                Result.Success(homeService.getTouristsRoutes() ?: emptyList())
                touristsRoutesDao.deleteAll()
                Log.i("TAG666", getTouristsRoutesFromRoom().toString())
                Result.Success(getTouristsRoutesFromRoom() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }

    override suspend fun getMansions(): Result<List<Mansions>> {
        return withContext(Dispatchers.IO) {
            runCatching {
//                Result.Success(homeService.getMansion() ?: emptyList())
                mansionsDao.deleteAll()
                Log.i("TAG666", getMansionsFromRoom().toString())
                Result.Success(getMansionsFromRoom() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }

    override suspend fun getAttractions(): Result<List<Attractions>> {
        return withContext(Dispatchers.IO) {
            runCatching {
//                Result.Success(homeService.getAttractions() ?: emptyList())
                attractionsDao.deleteAll()
                Log.i("TAG666", getAttractionsFromRoom().toString())
                Result.Success(getAttractionsFromRoom() ?: emptyList())
            }.getOrElse {
                Result.Error(it)
            }
        }
    }

    private fun getTouristsRoutesFromRoom(): List<TouristsRoutes>? {
        val touristsRoutes = homeService.getTouristsRoutes()

        touristsRoutes?.forEach {
            touristsRoutesDao.setTouristsRoutes(it.toDbModel(gson))
        }

        ArrayList<TouristsRoutes>().apply {
            touristsRoutesDao.getTouristsRoutes().forEach { this.add(it.toModel(gson)) }
            return this
        }
    }

    private fun getMansionsFromRoom(): List<Mansions>? {
        val mansions = homeService.getMansion()

        mansions?.forEach {
            mansionsDao.setMansions(it.toDbModel(gson))
        }

        ArrayList<Mansions>().apply {
            mansionsDao.getMansions().forEach { this.add(it.toModel(gson)) }
            return this
        }
    }

    private fun getAttractionsFromRoom(): List<Attractions>? {
        val attractions = homeService.getAttractions()

        attractions?.forEach {
            attractionsDao.setAttractions(it.toDbModel(gson))
        }

        ArrayList<Attractions>().apply {
            attractionsDao.getAttractions().forEach { this.add(it.toModel(gson)) }
            return this
        }
    }
}

