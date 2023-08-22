package com.gathigai.dvtweatherapp.data.database.dao

import androidx.room.*
import com.gathigai.dvtweatherapp.data.database.models.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Insert
    suspend fun insertCity(entity: CityEntity): Long

    @Insert
    suspend fun insertCities(vararg entities: CityEntity): List<Long>

    @Update
    suspend fun updateCities(vararg entities: CityEntity)

    @Upsert
    suspend fun upsertCities(vararg cityEntity: CityEntity)

    @Query(value = "SELECT * FROM cities")
    fun getCities(): Flow<List<CityEntity>>

    @Query(value = "SELECT * FROM cities WHERE is_current=true")
    fun getCurrentCity(): Flow<CityEntity>

    @Query("SELECT * FROM cities WHERE id = :cityId")
    fun getCityById(cityId: Long):Flow<CityEntity>

    @Query("SELECT * FROM cities WHERE citylongitude = :longitude AND citylatitude= :latitude")
    fun getCityByCoordinates(latitude: String, longitude: String): Flow<CityEntity>

    @Query(
        value = """
            DELETE FROM cities WHERE id in (:ids)
        """)
    suspend fun deleteCities(vararg ids: String)
}