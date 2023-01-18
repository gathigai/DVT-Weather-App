package com.gathigai.dvtweatherapp.di

import com.gathigai.dvtweatherapp.data.database.AppDatabase
import com.gathigai.dvtweatherapp.data.database.dao.CityDao
import com.gathigai.dvtweatherapp.data.database.dao.WeatherDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Provides
    fun providesCityDao(database: AppDatabase): CityDao = database.cityDao();

    @Provides
    fun providesWeatherDetailDao(
        database: AppDatabase
    ): WeatherDetailDao = database.weatherDetailDao();

}