package com.example.weatherapp.model

import android.content.Context
import com.example.weatherapp.common.listner.RequestCompleteListener
import com.example.weatherapp.common.network.ApiInterface
import com.example.weatherapp.common.network.RetrofitClient
import com.example.weatherapp.model.data_class.City
import com.example.weatherapp.model.data_class.WeatherInfoResponse
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WeatherInfoShowModelImpl(private val context: Context) : WeatherInfoShowModel {

    override fun getCityList(callback: RequestCompleteListener<MutableList<City>>) {
        try {
            val stream = context.assets.open("city_list.json")

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val tContents = String(buffer)

            val groupListType = object : TypeToken<ArrayList<City>>() {}.type
            val gson = GsonBuilder().create()
            val cityList: MutableList<City> = gson.fromJson(tContents, groupListType)

            callback.onRequestSuccess(cityList) //let presenter know the city list

        } catch (e: IOException) {
            e.printStackTrace()
            callback.onRequestFailed(e.localizedMessage!!) //let presenter know about failure
        }
    }

    override fun getWeatherInfo(name: String, latitude: Double, longitude: Double, callback: RequestCompleteListener<WeatherInfoResponse>) {

        val apiInterface: ApiInterface = RetrofitClient.client.create(ApiInterface::class.java)
        val call: Call<WeatherInfoResponse> = apiInterface.callApiForWeatherInfo(name, latitude, longitude)

        call.enqueue(object : Callback<WeatherInfoResponse> {

            // if retrofit network call success, this method will be triggered
            override fun onResponse(call: Call<WeatherInfoResponse>, response: Response<WeatherInfoResponse>) {
                if (response.body() != null)
                    callback.onRequestSuccess(response.body()!!) //let presenter know the weather information data
                else
                    callback.onRequestFailed(response.message()) //let presenter know about failure
            }

            // this method will be triggered if network call failed
            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!) //let presenter know about failure
            }
        })
    }
}