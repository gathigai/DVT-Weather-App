package com.gathigai.dvtweatherapp.data.repository

import com.gathigai.dvtweatherapp.data.api.WeatherApiService
import com.gathigai.dvtweatherapp.data.asDataModel
import com.gathigai.dvtweatherapp.data.database.dao.WeatherDetailDao
import com.gathigai.dvtweatherapp.data.database.models.WeatherDetailEntity
import com.gathigai.dvtweatherapp.domain.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherDataRepository @Inject constructor(
    private val weatherDetailDao: WeatherDetailDao,
    private val weatherApiService: WeatherApiService
) {

    val todayWeather = weatherDetailDao.getCurrentWeatherDetails()
    fun weatherForecast(city: City) = weatherDetailDao.getWeatherDetailsByCityAndCurrent(city.id!!, false)

    suspend fun getSingleLocationWeatherData(city: City) {
        withContext(Dispatchers.IO) {
            val locationCurrentData = weatherApiService.locationCurrentData(
                city.coordinates?.latitude!!, city.coordinates.longitude!!
            )
            val weatherDetail = locationCurrentData.asDataModel()
            weatherDetail.cityId = city.id
            weatherDetail.isCurrent = true
            weatherDetailDao.deleteCurrentWeatherDetails()
            weatherDetailDao.insertWeatherDetail(weatherDetail)
        }
    }

    suspend fun getLocationForecastWeatherData(city: City) {
        Timber.d("Getting forecast")
        withContext(Dispatchers.IO) {
            val locationForecastData = weatherApiService.locationForecastData(
                city.coordinates?.latitude!!, city.coordinates.longitude!!
            )



            val forecastWeather: List<WeatherDetailEntity> = locationForecastData.list
                .map {
                        val data = it.asDataModel()
                        data.cityId = city.id
                        data.isCurrent = false
                    return@map data
                }

            Timber.d("Forecast: ${forecastWeather.size}")
            weatherDetailDao.deleteWeatherDetailsByCityId(city.id!!)

            forecastWeather.forEach { forecastWeather -> weatherDetailDao.insertWeatherDetail(forecastWeather) }
//            weatherDetailDao.insertWeatherDetails(forecastWeather)
        }
    }


}