package com.example.weatherapp.common.network

import com.example.weatherapp.model.data_class.WeatherInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun callApiForWeatherInfo(@Query("q") cityId: String,@Query("lat") latitude: Double,@Query("lon") longitude: Double): Call<WeatherInfoResponse>
}