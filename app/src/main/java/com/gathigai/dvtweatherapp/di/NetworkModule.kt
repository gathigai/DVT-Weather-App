package com.gathigai.dvtweatherapp.di

import com.gathigai.dvtweatherapp.data.api.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideWeatherApiService(): WeatherApiService {
        return WeatherApiService.create();
    }
}