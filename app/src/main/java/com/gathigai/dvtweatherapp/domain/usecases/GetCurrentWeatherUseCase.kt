package com.gathigai.dvtweatherapp.domain.usecases

import com.gathigai.dvtweatherapp.data.database.models.WeatherDetailEntity
import com.gathigai.dvtweatherapp.data.database.models.asDomainModel
import com.gathigai.dvtweatherapp.data.repository.CityRepository
import com.gathigai.dvtweatherapp.data.repository.WeatherDataRepository
import com.gathigai.dvtweatherapp.domain.City
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    private val weatherDataRepository: WeatherDataRepository
) {
    suspend operator fun invoke(city: City) {
        val currentCityFlow = cityRepository.getCurrentCity()

        currentCityFlow.filter { currentCity: City -> currentCity.coordinates?.equals(city.coordinates) == true }
            .collect {weatherDataRepository.getSingleLocationWeatherData(it)}
    }
}