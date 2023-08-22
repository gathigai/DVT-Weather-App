package com.gathigai.dvtweatherapp.domain.usecases

import com.gathigai.dvtweatherapp.data.repository.CityRepository
import com.gathigai.dvtweatherapp.domain.City
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentCityUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {
    suspend operator fun invoke(): Flow<City> {
        return cityRepository.getCurrentCity()
    }
}