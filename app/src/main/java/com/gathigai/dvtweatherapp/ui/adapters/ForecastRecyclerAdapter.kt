package com.gathigai.dvtweatherapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gathigai.dvtweatherapp.data.database.models.WeatherDetailEntity
import com.gathigai.dvtweatherapp.databinding.ListItemForecastBinding
import com.gathigai.dvtweatherapp.ui.adapters.diff.ForecastDiffCallback
import com.gathigai.dvtweatherapp.util.databinding.BindingConverter
import com.gathigai.dvtweatherapp.util.view.EmptyStateRecyclerViewAdapter

class ForecastRecyclerAdapter(
    emptyView: View,
    list: View,
    val context: Context,
    val onItemClickListener: OnItemClickedListener<WeatherDetailEntity>
) : EmptyStateRecyclerViewAdapter<WeatherDetailEntity, ForecastRecyclerAdapter.ViewHolder>(
    emptyView,
    list,
    ForecastDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemForecastBinding
            .inflate(LayoutInflater
                .from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(private var binding: ListItemForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(weatherDetailEntity: WeatherDetailEntity){
                binding.root.setOnClickListener{onItemClickListener.onItemClicked(weatherDetailEntity)}
                binding.data = weatherDetailEntity
                binding.converter = BindingConverter()
                binding.executePendingBindings()
            }
        }

    interface OnItemSelectedListener {
        fun onItemSelected(item: WeatherDetailEntity)
    }
}