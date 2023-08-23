package com.gathigai.dvtweatherapp.data.api

import com.gathigai.dvtweatherapp.data.ForecastDataResponse
import com.gathigai.dvtweatherapp.data.SingleLocationDataResponse
import com.gathigai.dvtweatherapp.data.datatype.ApiUnitOfMeasurement
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("weather")
    suspend fun locationCurrentData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: ApiUnitOfMeasurement = ApiUnitOfMeasurement.METRIC,
        @Query("appid") clientId: String = "c5c113bc55983bc0971a23aa1f0aa7e9" //c5c113bc55983bc0971a23aa1f0aa7e9
    ): SingleLocationDataResponse

    @GET("forecast")
    suspend fun locationForecastData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: ApiUnitOfMeasurement = ApiUnitOfMeasurement.METRIC,
        @Query("appid") clientId: String = "c5c113bc55983bc0971a23aa1f0aa7e9" //BuildConfig.UNSPLASH_ACCESS_KEY
    ): ForecastDataResponse

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

        fun create(): WeatherApiService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}