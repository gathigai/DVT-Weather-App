package com.gathigai.dvtweatherapp.domain.usecases

import com.gathigai.dvtweatherapp.data.repository.CityRepository
import com.gathigai.dvtweatherapp.domain.City
import kotlinx.coroutines.flow.firstOrNull
import timber.log.Timber
import javax.inject.Inject

class CreateCityUseCase @Inject constructor(
    private val cityRepository: CityRepository
) {
    suspend operator fun invoke(city: City) {
        Timber.i("Create City Use case $city")
        if (city.coordinates?.latitude.isNullOrEmpty()) {
            Timber.i("Create City Use case latitude is empty")
        } else if (city.coordinates?.longitude.isNullOrEmpty()) {
            Timber.i("Create City Use case longitude is empty")
        } else {
            val existingCityFlow = city.coordinates?.let { coordinates ->
                cityRepository.getCityByCoordinates(
                    coordinates.latitude!!,
                    coordinates.longitude!!
                )
            }
            val existingCity = existingCityFlow?.firstOrNull()

            if (existingCity != null
                && existingCity.coordinates?.latitude.equals(city.coordinates!!.latitude)
                && existingCity.coordinates?.longitude.equals(city.coordinates!!.longitude)
            ) {
                Timber.d("The city exists")
            } else {
                Timber.i("Create city")
                cityRepository.createCity(city)
            }
        }
    }
}