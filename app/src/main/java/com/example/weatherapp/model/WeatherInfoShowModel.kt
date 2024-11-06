package com.example.weatherapp.model

import com.example.weatherapp.common.listner.RequestCompleteListener
import com.example.weatherapp.model.data_class.City
import com.example.weatherapp.model.data_class.WeatherInfoResponse

interface WeatherInfoShowModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfo(name: String,latitude: Double,longitude: Double, callback: RequestCompleteListener<WeatherInfoResponse>)
}