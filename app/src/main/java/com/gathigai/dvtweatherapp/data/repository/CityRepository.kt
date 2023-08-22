package com.gathigai.dvtweatherapp.data.repository

import com.gathigai.dvtweatherapp.data.api.WeatherApiService
import com.gathigai.dvtweatherapp.data.database.dao.CityDao
import com.gathigai.dvtweatherapp.data.database.models.asDomainModel
import com.gathigai.dvtweatherapp.domain.City
import com.gathigai.dvtweatherapp.domain.asDataModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CityRepository @Inject constructor(
    private val cityDao: CityDao,
    private val weatherApiService: WeatherApiService
) {

    fun getCities() = cityDao.getCities();

    fun getCurrentCity() = cityDao.getCurrentCity()
        .map { it.asDomainModel() };

    fun getCityByCoordinates(latitude: String, longitude: String) =
        cityDao.getCityByCoordinates(latitude, longitude)

    fun getCityById(cityId: Long) = cityDao.getCityById(cityId)


    suspend fun createCity(city: City): Long {
        return cityDao.insertCity(city.asDataModel())
    }

}