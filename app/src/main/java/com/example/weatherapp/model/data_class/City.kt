package com.example.weatherapp.model.data_class

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class City(
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("name")
        val name: String = "",
        @SerializedName("country")
        val country: String = "",
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,

): Serializable