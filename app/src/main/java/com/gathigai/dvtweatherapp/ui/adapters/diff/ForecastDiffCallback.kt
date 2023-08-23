package com.gathigai.dvtweatherapp.ui.adapters.diff

import androidx.recyclerview.widget.DiffUtil
import com.gathigai.dvtweatherapp.data.database.models.WeatherDetailEntity

class ForecastDiffCallback : DiffUtil.ItemCallback<WeatherDetailEntity>() {
    override fun areItemsTheSame(
        oldItem: WeatherDetailEntity,
        newItem: WeatherDetailEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: WeatherDetailEntity,
        newItem: WeatherDetailEntity
    ): Boolean {
        return oldItem == newItem
    }
}