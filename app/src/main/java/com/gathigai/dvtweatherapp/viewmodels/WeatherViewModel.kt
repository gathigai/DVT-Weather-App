package com.gathigai.dvtweatherapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gathigai.dvtweatherapp.data.Coordinates
import com.gathigai.dvtweatherapp.data.repository.CityRepository
import com.gathigai.dvtweatherapp.data.repository.WeatherDataRepository
import com.gathigai.dvtweatherapp.domain.City
import com.gathigai.dvtweatherapp.domain.usecases.CreateCityUseCase
import com.gathigai.dvtweatherapp.domain.usecases.RequestWeatherForecastUseCase
import com.gathigai.dvtweatherapp.domain.usecases.RequestCityWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val requestCityWeatherUseCase: RequestCityWeatherUseCase,
    private val requestWeatherForecastUseCase: RequestWeatherForecastUseCase,
    private val createCityUseCase: CreateCityUseCase,
    private val weatherDataRepository: WeatherDataRepository,
    private val cityRepository: CityRepository
): ViewModel() {

    val city = City(
        name = "london",
        isFavourite = false,
        isCurrent = true,
        coordinates = Coordinates("10.99", "44.34"),
        country = null,
        timezone = null,
        sunrise = null,
        sunset = null,
        id = null
    )

    val currentCity = cityRepository.getCurrentCity();
    val currentWeather = weatherDataRepository.todayWeather
   /*fun forecastWeather(): Flow<List<WeatherDetailEntity>>{
       var flow: Flow<List<WeatherDetailEntity>> = emptyFlow()
       viewModelScope.launch {
           currentCity.collect{currentCity ->
               Timber.d("Get Forecast for $currentCity")
               flow = weatherDataRepository.weatherForecast(currentCity)
           }
       }
       return flow
   }*/

    fun forecastWeather(city: City) = weatherDataRepository.getCityWeatherForecast(city)

    fun getLocationData(){

        viewModelScope.launch {
            createCityUseCase.invoke(city)
            val currentCity = cityRepository.getCurrentCity()
            currentCity.collect{
                requestWeatherForecastUseCase(it)
                requestCityWeatherUseCase(it)
            }
        }
    }
}