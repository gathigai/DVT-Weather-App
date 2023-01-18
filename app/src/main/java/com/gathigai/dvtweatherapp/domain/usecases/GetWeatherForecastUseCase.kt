package com.gathigai.dvtweatherapp.domain.usecases

import com.gathigai.dvtweatherapp.data.database.models.CityEntity
import com.gathigai.dvtweatherapp.data.database.models.asDomainModel
import com.gathigai.dvtweatherapp.data.repository.CityRepository
import com.gathigai.dvtweatherapp.data.repository.WeatherDataRepository
import com.gathigai.dvtweatherapp.domain.City
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
    private val weatherDataRepository: WeatherDataRepository
) {
    suspend operator fun invoke(city: City){
         weatherDataRepository.getLocationForecastWeatherData(city)
    }
}