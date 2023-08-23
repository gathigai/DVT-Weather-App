package com.gathigai.dvtweatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.gathigai.dvtweatherapp.R
import com.gathigai.dvtweatherapp.data.Coordinates
import com.gathigai.dvtweatherapp.data.database.models.WeatherDetailEntity
import com.gathigai.dvtweatherapp.databinding.FragmentFirstBinding
import com.gathigai.dvtweatherapp.domain.City
import com.gathigai.dvtweatherapp.ui.adapters.ForecastRecyclerAdapter
import com.gathigai.dvtweatherapp.util.databinding.BindingConverter
import com.gathigai.dvtweatherapp.util.view.EmptyStateRecyclerViewAdapter
import com.gathigai.dvtweatherapp.viewmodels.CityViewModel
import com.gathigai.dvtweatherapp.viewmodels.WeatherViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    private val cityViewModel: CityViewModel by viewModels()

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var forecastRecyclerAdapter: ForecastRecyclerAdapter

    lateinit var locationManager: LocationManager

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var locationRequest: LocationRequest

    private lateinit var locationCallback: LocationCallback

    private var currentLocation: Location? = null

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private var currentCity: City? = null

    var PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.converter = BindingConverter()

        locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cityViewModel.currentCity.collect { value: City? ->
                    run {
                        if (value != null) {
                            currentCity = value
                            Timber.d(currentCity.toString())
                        }
                    }
                }
            }
        }

        with(binding.forecastRecyclerView) {
            val mLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = mLayoutManager
            adapter = forecastRecyclerAdapter

        }

        if (isLocationPermissionGranted()) {
            getLocationAndCity()
        }
        val todayWeather = weatherViewModel.currentWeather


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                cityViewModel.currentCity.collect { currentCity ->
                    Timber.d("Current City: $currentCity")

                    if (currentCity.id != null) {

                        weatherViewModel.getLocationData()

                        viewLifecycleOwner.lifecycleScope.launch {
                            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                weatherViewModel.forecastWeather(currentCity).collect {
                                    it.forEach { Timber.d("Forecast ${it}") }
                                    forecastRecyclerAdapter.submitList(it)
                                }
                            }
                        }


                        todayWeather.collect { todayWeather ->
                            run {
                                binding.weather = todayWeather
                                if (todayWeather != null && todayWeather.weather.isNotEmpty()) {
                                    when (todayWeather.weather[0].main) {
                                        "Clouds", "Mist", "Smoke", "Haze", "Dust", "Fog", "Sand", "Ash", "Squall", "Tornado" -> {
                                            binding.topView.setBackgroundResource(R.drawable.forest_cloudy)
                                            binding.bottomView.setBackgroundColor(
                                                resources.getColor(
                                                    R.color.cloudy_background,
                                                    null
                                                )
                                            )
                                        }

                                        "Rain", "Thunderstorm", "Drizzle", "Snow" -> {
                                            binding.topView.setBackgroundResource(R.drawable.forest_rainy)
                                            binding.bottomView.setBackgroundColor(
                                                resources.getColor(
                                                    R.color.rainy_background,
                                                    null
                                                )
                                            )
                                        }

                                        "Clear" -> {
                                            binding.topView.setBackgroundResource(R.drawable.forest_sunny)
                                            binding.bottomView.setBackgroundColor(
                                                resources.getColor(
                                                    R.color.sunny_background,
                                                    null
                                                )
                                            )
                                        }

                                        else -> {
                                            binding.topView.setBackgroundResource(R.drawable.forest_sunny)
                                            binding.bottomView.setBackgroundColor(
                                                resources.getColor(
                                                    R.color.sunny_background,
                                                    null
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Timber.tag("TAG1").i("onViewCreated: ")
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (::fusedLocationProviderClient.isInitialized) {
            val removeTask = fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            removeTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("Location Callback removed.")
                } else {
                    Timber.d("Failed to remove Location Callback.")
                }
            }
        }
    }

    private fun hasPermissions(context: Context, permissions: Array<String>): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }

    private fun isLocationPermissionGranted(): Boolean {
        return if (!hasPermissions(requireContext(), PERMISSIONS)) {
            permReqLauncher.launch(PERMISSIONS)
            false
        } else {
            true
        }


    }

    private fun hasGps() = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    private fun hasNetwork(): Boolean =
        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    @SuppressLint("MissingPermission")
    private fun getLocationAndCity() {
        val city = City()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        locationRequest = LocationRequest.Builder(TimeUnit.SECONDS.toMillis(60))
            .setMinUpdateIntervalMillis(TimeUnit.SECONDS.toMillis(30))
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    currentLocation = it

                    latitude = currentLocation?.latitude ?: 0.0
                    longitude = currentLocation?.longitude ?: 0.0

                    Timber.d("Current location is:\n lat: $latitude\n long: $longitude.")

                    if (currentCity != null && (currentCity?.coordinates == null ||
                                (!currentCity?.coordinates!!.latitude.equals(
                                    latitude.toString(),
                                    true
                                ) &&
                                        !currentCity?.coordinates!!.longitude.equals(
                                            longitude.toString(),
                                            true
                                        )))
                    ) {

                        val coordinates = Coordinates()
                        coordinates.latitude = latitude.toString()
                        coordinates.longitude = longitude.toString()
                        city.coordinates = coordinates
                        city.isCurrent = true

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            Geocoder(requireContext()).getFromLocation(
                                latitude, longitude, 1
                            ) { addresses ->
                                Timber.d("Address found is: ${addresses.toString()}")
                                if (addresses.isNotEmpty()) {
                                    coordinates.latitude = addresses[0].latitude.toString()
                                    coordinates.longitude = addresses[0].longitude.toString()

                                    city.name = addresses[0].thoroughfare
                                    city.country = addresses[0].countryName
                                    city.coordinates = coordinates
                                }
                            }
                        } else {
                            Geocoder(requireContext()).getFromLocation(latitude, longitude, 1)
                                .let { addresses ->
                                    Timber.d("Address found is: ${addresses.toString()}")
                                    if (!addresses.isNullOrEmpty()) {
                                        coordinates.latitude = addresses[0].latitude.toString()
                                        coordinates.longitude = addresses[0].longitude.toString()

                                        city.name = addresses[0].thoroughfare
                                        city.country = addresses[0].countryName
                                        city.coordinates = coordinates
                                    }
                                }
                        }

                        cityViewModel.createCity(city)
                    }
                } ?: {
                    Timber.d("Location information isn't available.")
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private val permReqLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.entries.all {
                it.value
            }
            if (granted) {
                getLocationAndCity()
            }
        }
}