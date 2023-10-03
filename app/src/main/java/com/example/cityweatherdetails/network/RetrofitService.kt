package com.example.cityweatherdetails.network

import com.example.cityweatherdetails.util.Constants
import com.example.cityweatherdetails.model.WeatherDetailsModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("weather")
    suspend fun getCityWeatherDetails(@Query("q") p: String, @Query("appid") appid: String) : Response<WeatherDetailsModel>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}