package com.gathigai.dvtweatherapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gathigai.dvtweatherapp.data.database.models.WeatherDetailEntity
import com.gathigai.dvtweatherapp.databinding.FragmentFirstBinding
import com.gathigai.dvtweatherapp.domain.City
import com.gathigai.dvtweatherapp.ui.adapters.ForecastRecyclerAdapter
import com.gathigai.dvtweatherapp.util.databinding.BindingConverter
import com.gathigai.dvtweatherapp.util.view.EmptyStateRecyclerViewAdapter
import com.gathigai.dvtweatherapp.viewmodels.CurrentWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private val currentWeatherViewModel: CurrentWeatherViewModel by viewModels()

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var forecastRecyclerAdapter: ForecastRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.converter = BindingConverter()

        forecastRecyclerAdapter = ForecastRecyclerAdapter(
            binding.emptyForecastView,
            binding.forecastRecyclerView,
            requireContext(),
            object : EmptyStateRecyclerViewAdapter.OnItemClickedListener<WeatherDetailEntity> {
                override fun onItemClicked(item: WeatherDetailEntity) {

                }

            }
        )

        forecastRecyclerAdapter.submitList(emptyList())

        with(binding.forecastRecyclerView){
            val mLayoutManager = LinearLayoutManager(requireContext())
            layoutManager= mLayoutManager
            adapter = forecastRecyclerAdapter

        }

        currentWeatherViewModel.getLocationData();
        val todayWeather = currentWeatherViewModel.currentWeather

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){

                Timber.tag("TAG").d("onViewCreated: ")

                currentWeatherViewModel.currentCity.collect {currentCity ->
                    Timber.e("Current City: $currentCity")

                    viewLifecycleOwner.lifecycleScope.launch {
                        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                            currentWeatherViewModel.forecastWeather(currentCity).collect {
                                it.forEach { Timber.d("Forecast ${it}") }
                                forecastRecyclerAdapter.submitList(it)
                            }
                        }
                    }

                    todayWeather.collect { todayWeather -> binding.weather = todayWeather }
                }

                Timber.tag("TAG1").i("onViewCreated: ")
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}