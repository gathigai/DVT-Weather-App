package com.gathigai.dvtweatherapp.domain.usecases

import com.gathigai.dvtweatherapp.data.repository.WeatherDataRepository
import com.gathigai.dvtweatherapp.domain.City
import javax.inject.Inject

class GetCityWeatherUseCase @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository
) {
    suspend operator fun invoke(city: City) {
        return weatherDataRepository.requestSingleLocationWeatherData(city)
    }
}