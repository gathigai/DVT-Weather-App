package com.gathigai.dvtweatherapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gathigai.dvtweatherapp.data.Coordinates
import com.gathigai.dvtweatherapp.domain.City
import com.gathigai.dvtweatherapp.domain.usecases.CreateCityUseCase
import com.gathigai.dvtweatherapp.domain.usecases.GetCurrentCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val createCityUseCase: CreateCityUseCase,
    private val currentCityUseCase: GetCurrentCityUseCase
) : ViewModel() {

    val currentCity: Flow<City> = flow {
        val city = currentCityUseCase.invoke()
        emitAll(city)
    }

    fun createCity(name: String, latitude: Double, longitude: Double, isCurrent: Boolean) {
        val city = City(
            name = name,
            isFavourite = false,
            isCurrent = isCurrent,
            coordinates = Coordinates(latitude.toString(), longitude.toString()),
            country = null,
            timezone = null,
            sunrise = null,
            sunset = null,
            id = null
        )

        createCity(city)
    }

    fun createCity(city: City) {
        if (city.name.isNullOrEmpty() && city.coordinates == null) {
            Timber.i("City cannot be created when null")
        } else {
            viewModelScope.launch {
                createCityUseCase.invoke(city)
            }
        }
    }
}