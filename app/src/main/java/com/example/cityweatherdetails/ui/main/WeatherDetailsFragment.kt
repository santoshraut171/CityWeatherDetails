package com.example.cityweatherdetails.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.cityweatherdetails.R
import com.example.cityweatherdetails.model.WeatherDetailsModel
import com.example.cityweatherdetails.network.RetrofitService
import com.example.cityweatherdetails.network.WeatherRepository

class WeatherDetailsFragment : Fragment() {

    private lateinit var viewModel: WeatherDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retrofitService = RetrofitService.getInstance()
        val repository = WeatherRepository(retrofitService)
        viewModel = ViewModelProvider(this, WeatherDetailsViewModelFactory(repository)).get(WeatherDetailsViewModel::class.java)

//        activity?.findViewById<Button>(R.id.searchButton)?.setOnClickListener {
//            viewModel.getWeatherDetails((activity?.findViewById<EditText>(R.id.editTextCityName))?.text.toString())
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_weather_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getView()?.findViewById<Button>(R.id.searchButton)?.setOnClickListener {
            val inputText = view.findViewById<EditText>(R.id.editTextCityName)?.text.toString()
            if (inputText.isEmpty()) {
                Toast.makeText(activity, "Please Enter City Name", Toast.LENGTH_LONG).show()
            } else {
                viewModel.getWeatherDetails(inputText)
            }
        }

        viewModel.weatherDetailsModel.observeForever { populateWeatherData(it, view) }
    }

    private fun populateWeatherData(weatherDetailsModel: WeatherDetailsModel, view: View) {
        view.findViewById<LinearLayout>(R.id.weatherDetailsLayout).visibility = View.VISIBLE
        view.findViewById<TextView>(R.id.cityDetails).text = weatherDetailsModel.name.plus(", ${weatherDetailsModel.sys.country}")
        view.findViewById<TextView>(R.id.weatherTitle).text = weatherDetailsModel.weather[0].main.plus(": ${weatherDetailsModel.weather[0].description}")
        view.findViewById<TextView>(R.id.tempMin).text = "Temp Min: ${weatherDetailsModel.main.temp_max}"
        view.findViewById<TextView>(R.id.tempMax).text = "Temp Max: ${weatherDetailsModel.main.temp_max}"
        view.findViewById<TextView>(R.id.pressure).text = "Pressure: ${weatherDetailsModel.main.pressure}"
        view.findViewById<TextView>(R.id.humidity).text = "Humidity: ${weatherDetailsModel.main.humidity}"
        view.findViewById<TextView>(R.id.windSpeed).text = "Wind Speed: ${weatherDetailsModel.wind.speed}"
        view.findViewById<TextView>(R.id.windSpeed).text = "Wind Speed: ${weatherDetailsModel.wind.speed}"
        view.findViewById<TextView>(R.id.sunrise).text = "Sunrise: ${weatherDetailsModel.sys.sunrise}"
        view.findViewById<TextView>(R.id.sunset).text = "Sunset: ${weatherDetailsModel.sys.sunset}"
    }
}