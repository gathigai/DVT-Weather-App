package com.gathigai.dvtweatherapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gathigai.dvtweatherapp.data.database.dao.CityDao
import com.gathigai.dvtweatherapp.data.database.dao.WeatherDetailDao
import com.gathigai.dvtweatherapp.data.database.models.CityEntity
import com.gathigai.dvtweatherapp.data.database.models.WeatherDetailEntity
import com.gathigai.dvtweatherapp.util.WeatherListConverter

@Database(
    entities = [
        CityEntity::class,
        WeatherDetailEntity::class
    ],
    version = 9,
    exportSchema = true
)
@TypeConverters(
    WeatherListConverter::class
)
abstract class AppDatabase: RoomDatabase(){
    abstract fun cityDao(): CityDao
    abstract fun weatherDetailDao(): WeatherDetailDao
}