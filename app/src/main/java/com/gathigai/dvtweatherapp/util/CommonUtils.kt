package com.gathigai.dvtweatherapp.util

import android.icu.text.SimpleDateFormat
import java.util.Locale

fun getDayOfWeek(timestamp: Int): String {
    return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
}
