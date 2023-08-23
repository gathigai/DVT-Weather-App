package com.gathigai.dvtweatherapp.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.gathigai.dvtweatherapp.data.Clouds
import com.gathigai.dvtweatherapp.data.Coordinates
import com.gathigai.dvtweatherapp.data.Main
import com.gathigai.dvtweatherapp.data.Rain
import com.gathigai.dvtweatherapp.data.Snow
import com.gathigai.dvtweatherapp.data.Sys
import com.gathigai.dvtweatherapp.data.Weather
import com.gathigai.dvtweatherapp.data.Wind

@Entity(
    tableName = "weather_details",
    foreignKeys = [
        ForeignKey(
            entity = CityEntity::class,
            parentColumns = ["id"],
            childColumns = ["city_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["city_id"])
    ]
)
data class WeatherDetailEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo(name = "city_id")
    var cityId: Long?,
    @ColumnInfo(name = "is_current")
    var isCurrent: Boolean = false,
    @Embedded(prefix = "coordinates")
    val coordinates: Coordinates?,
    @ColumnInfo(name = "weathers")
    val weather: ArrayList<Weather> = arrayListOf(),
    val base: String?,
    @Embedded(prefix = "main")
    val main: Main?,
    val visibility: Int?,
    @Embedded(prefix = "wind")
    val wind: Wind?,
    @Embedded(prefix = "rain")
    val rain: Rain?,
    @Embedded(prefix = "snow")
    val snow: Snow?,
    @Embedded(prefix = "clouds")
    val clouds: Clouds?,
    val dataCalculationTime: String?,
    @Embedded(prefix = "sys")
    val sys: Sys?,
    val timezone: Int?,
    val apiId: Int?,
    val name: String?,
    val cod: Int?
)