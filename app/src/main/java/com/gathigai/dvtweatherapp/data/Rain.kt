package com.gathigai.dvtweatherapp.data

import com.google.gson.annotations.SerializedName

data class Rain(
    @field:SerializedName("1h") var lastOneHour: Double? = null,
    @field:SerializedName("3h") var lastThreeHours: Double? = null
)