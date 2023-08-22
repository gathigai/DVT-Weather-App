package com.gathigai.dvtweatherapp.data

import com.google.gson.annotations.SerializedName

data class Coordinates(
    @field:SerializedName("lat") var latitude: String? = null,
    @field:SerializedName("lon") var longitude: String? = null
)