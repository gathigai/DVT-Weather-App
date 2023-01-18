package com.gathigai.dvtweatherapp.util.databinding

import androidx.databinding.InverseMethod

class BindingConverter {

    @InverseMethod("stringToDouble")
    fun doubleToString(value: Double?): String = value?.toString() ?: ""

    fun stringToDouble(value: String?): Double? {
        return if (value.isNullOrEmpty()) {
            0.0
        } else {
            value.toDouble()
        }
    }
}