package com.gathigai.dvtweatherapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gathigai.dvtweatherapp.data.repository.CityRepository
import com.gathigai.dvtweatherapp.data.repository.WeatherDataRepository
import com.gathigai.dvtweatherapp.domain.City
import com.gathigai.dvtweatherapp.domain.usecases.RequestCityWeatherUseCase
import com.gathigai.dvtweatherapp.domain.usecases.RequestWeatherForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val requestCityWeatherUseCase: RequestCityWeatherUseCase,
    private val requestWeatherForecastUseCase: RequestWeatherForecastUseCase,
    private val weatherDataRepository: WeatherDataRepository,
    private val cityRepository: CityRepository
) : ViewModel() {

    val currentWeather = weatherDataRepository.todayWeather

    fun forecastWeather(city: City) = weatherDataRepository.getCityWeatherForecast(city)

    fun getLocationData() {

        viewModelScope.launch {
            val currentCity = cityRepository.getCurrentCity()
            currentCity.collect {
                requestWeatherForecastUseCase(it)
                requestCityWeatherUseCase(it)
            }
        }
    }
}