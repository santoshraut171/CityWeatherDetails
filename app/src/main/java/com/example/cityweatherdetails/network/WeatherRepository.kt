package com.example.cityweatherdetails.network

class WeatherRepository constructor(private val retrofitService: RetrofitService) {
    suspend fun getCityWeatherDetails(city: String, appID: String) = retrofitService.getCityWeatherDetails(city, appID)
}