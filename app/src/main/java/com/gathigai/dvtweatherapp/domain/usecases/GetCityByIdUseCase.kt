package com.gathigai.dvtweatherapp.domain.usecases

import com.gathigai.dvtweatherapp.data.database.models.CityEntity
import com.gathigai.dvtweatherapp.data.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCityByIdUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {
    suspend operator fun invoke(cityId: Long): Flow<CityEntity> {
        return cityRepository.getCityById(cityId)
    }
}