package com.gathigai.dvtweatherapp.domain.usecases

import com.gathigai.dvtweatherapp.data.repository.CityRepository
import com.gathigai.dvtweatherapp.data.repository.WeatherDataRepository
import com.gathigai.dvtweatherapp.domain.City
import javax.inject.Inject

class RequestCityWeatherUseCase @Inject constructor(
    private val cityRepository: CityRepository,
    private val weatherDataRepository: WeatherDataRepository
) {
    suspend operator fun invoke(city: City) {
        weatherDataRepository.requestSingleLocationWeatherData(city)
    }
}