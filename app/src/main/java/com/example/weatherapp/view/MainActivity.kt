package com.example.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.weatherapp.common.utils.PrefRepository
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.databinding.LayoutInputPartBinding
import com.example.weatherapp.databinding.LayoutWeatherAdditionalInfoBinding
import com.example.weatherapp.databinding.LayoutWeatherBasicInfoBinding
import com.example.weatherapp.model.WeatherInfoShowModel
import com.example.weatherapp.model.WeatherInfoShowModelImpl
import com.example.weatherapp.model.data_class.City
import com.example.weatherapp.model.data_class.WeatherData
import com.example.weatherapp.viewmodel.WeatherInfoViewModel
import com.example.weatherapp.common.utils.convertToListOfCityName


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var model: WeatherInfoShowModel
    private lateinit var viewModel: WeatherInfoViewModel
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var layoutInputPartBinding: LayoutInputPartBinding
    private lateinit var layoutWeatherBasicInfoBinding: LayoutWeatherBasicInfoBinding
    private lateinit var layoutWeatherAdditionalInfoBinding: LayoutWeatherAdditionalInfoBinding

    private var cityList: MutableList<City> = mutableListOf()
    private lateinit var prefRepository: PrefRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        layoutInputPartBinding = mBinding.layoutInput
        layoutWeatherBasicInfoBinding = mBinding.layoutWeatherBasic
        layoutWeatherAdditionalInfoBinding = mBinding.layoutWeatherAdditional
        setContentView(mBinding.root)

        // initialize model. (I know we should not initialize model in View. But for simplicity...)
        model = WeatherInfoShowModelImpl(this)
        // initialize ViewModel
        viewModel = ViewModelProviders.of(this).get(WeatherInfoViewModel::class.java)
        prefRepository = PrefRepository(this)

        // set LiveData and View click listeners before the call for data fetching
        setLiveDataListeners()
        setViewClickListener()

        /**
         * Fetch city list when Activity open.
         * It's not a very good way that, passing model in every methods of ViewModel. For the sake
         * of simplicity I did so. In real production level App, we can inject out model to ViewModel
         * as a parameter by any dependency injection library like Dagger.
         */
        viewModel.getCityList(model)
    }

    private fun setViewClickListener() {

        // View Weather button click listener
        layoutInputPartBinding.btnViewWeather.setOnClickListener {
            callWeatherInfo()
        }
    }

    private fun callWeatherInfo(){
        val selectedCityId = cityList[layoutInputPartBinding.spinner.selectedItemPosition].name
        val latitude = cityList[layoutInputPartBinding.spinner.selectedItemPosition].latitude
        val longitude = cityList[layoutInputPartBinding.spinner.selectedItemPosition].longitude
        viewModel.getWeatherInfo(selectedCityId,latitude,longitude, model) // fetch weather info
        prefRepository.setSelectedId(layoutInputPartBinding.spinner.selectedItemPosition)
    }

    private fun setLiveDataListeners() {

        viewModel.cityListLiveData.observe(this, object : Observer<MutableList<City>>{
            override fun onChanged(cities: MutableList<City>) {
                setCityListSpinner(cities)
            }
        })

        /**
         * If ViewModel failed to fetch City list from data source, this LiveData will be triggered.
         * I know it's not good to make separate LiveData both for Success and Failure, but for sake
         * of simplification I did it. We can handle all of our errors from our Activity or Fragment
         * Base classes. Another way is: using a Generic wrapper class where you can set the success
         * or failure status for any types of data model.
         *
         * Here I've used lambda expression to implement Observer interface in second parameter.
         */
        viewModel.cityListFailureLiveData.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        })

        /**
         * ProgressBar visibility will be handled by this LiveData. ViewModel decides when Activity
         * should show ProgressBar and when hide.
         *
         * Here I've used lambda expression to implement Observer interface in second parameter.
         */
        viewModel.progressBarLiveData.observe(this, Observer { isShowLoader ->
            if (isShowLoader)
                mBinding.progressBar.visibility = View.VISIBLE
            else
                mBinding.progressBar.visibility = View.GONE
        })

        /**
         * This method will be triggered when ViewModel successfully receive WeatherData from our
         * data source (I mean Model). Activity just observing (subscribing) this LiveData for showing
         * weather information on UI. ViewModel receives Weather data API response from Model via
         * Callback method of Model. Then ViewModel apply some business logic and manipulate data.
         * Finally ViewModel PUSH WeatherData to `weatherInfoLiveData`. After PUSHING into it, below
         * method triggered instantly! Then we set the data on UI.
         *
         * Here I've used lambda expression to implement Observer interface in second parameter.
         */
        viewModel.weatherInfoLiveData.observe(this, Observer { weatherData ->
            setWeatherInfo(weatherData)
        })

        /**
         * If ViewModel faces any error during Weather Info fetching API call by Model, then PUSH the
         * error message into `weatherInfoFailureLiveData`. After that, this method will be triggered.
         * Then we will hide the output view and show error message on UI.
         *
         * Here I've used lambda expression to implement Observer interface in second parameter.
         */
        viewModel.weatherInfoFailureLiveData.observe(this, Observer { errorMessage ->
            mBinding.outputGroup.visibility = View.GONE
            mBinding.tvErrorMessage.visibility = View.VISIBLE
            mBinding.tvErrorMessage.text = errorMessage
        })
    }

    private fun setCityListSpinner(cityList: MutableList<City>) {
        this.cityList = cityList
        layoutInputPartBinding.spinner.onItemSelectedListener = this
        val arrayAdapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            this.cityList.convertToListOfCityName()
        )

        layoutInputPartBinding.spinner.adapter = arrayAdapter

        if (prefRepository.getSelectedId() >0) {
            layoutInputPartBinding.spinner.setSelection(prefRepository.getSelectedId())
        }
    }

    private fun setWeatherInfo(weatherData: WeatherData) {
        mBinding.outputGroup.visibility = View.VISIBLE
        mBinding.tvErrorMessage.visibility = View.GONE

        layoutWeatherBasicInfoBinding.tvDateTime.text = weatherData.dateTime
        layoutWeatherBasicInfoBinding.tvTemperature.text = weatherData.temperature
        layoutWeatherBasicInfoBinding.tvCityCountry.text = weatherData.cityAndCountry
        Glide.with(this).load(weatherData.weatherConditionIconUrl).into(layoutWeatherBasicInfoBinding.ivWeatherCondition)
        layoutWeatherBasicInfoBinding.tvWeatherCondition.text = weatherData.weatherConditionIconDescription

        layoutWeatherAdditionalInfoBinding.tvHumidityValue.text = weatherData.humidity
        layoutWeatherAdditionalInfoBinding.tvPressureValue.text = weatherData.pressure
        layoutWeatherAdditionalInfoBinding.tvVisibilityValue.text = weatherData.visibility
        layoutWeatherAdditionalInfoBinding.tvTempMinValue.text = weatherData.tempMin
        layoutWeatherAdditionalInfoBinding.tvTempMaxValue.text = weatherData.tempMax
        layoutWeatherAdditionalInfoBinding.tvFeelsLikeValue.text = weatherData.feelsLike

        layoutWeatherAdditionalInfoBinding.tvSunriseTime.text = weatherData.sunset
        layoutWeatherAdditionalInfoBinding.tvSunsetTime.text = weatherData.sunrise
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        callWeatherInfo()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}
