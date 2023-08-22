package com.gathigai.dvtweatherapp.util.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import com.gathigai.dvtweatherapp.util.getDayOfWeek

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