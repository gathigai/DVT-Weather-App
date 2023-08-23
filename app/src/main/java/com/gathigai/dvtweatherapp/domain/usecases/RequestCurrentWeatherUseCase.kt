package com.gathigai.dvtweatherapp.domain.usecases

import com.gathigai.dvtweatherapp.data.repository.CityRepository
import com.gathigai.dvtweatherapp.data.repository.WeatherDataRepository
import com.gathigai.dvtweatherapp.domain.City
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class RequestCurrentWeatherUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    private val weatherDataRepository: WeatherDataRepository
) {
    suspend operator fun invoke(city: City) {
        val currentCityFlow = cityRepository.getCurrentCity()

        currentCityFlow.filter { currentCity: City? -> currentCity?.coordinates?.equals(city.coordinates) == true }
            .collect { weatherDataRepository.requestSingleLocationWeatherData(it!!) }
    }
}