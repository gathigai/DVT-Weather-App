package com.gathigai.dvtweatherapp.data

import com.google.gson.annotations.SerializedName

data class Clouds(
    @field:SerializedName("all") var all: Int? = null
)