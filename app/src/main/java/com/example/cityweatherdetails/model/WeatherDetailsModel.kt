package com.example.cityweatherdetails.model

data class WeatherDetailsModel(
    var coord: Coord,
    var weather: List<Weather>,
    var base: String,
    var main: Main,
    var visibility: Long,
    var wind: Wind,
    var rain: Rain,
    var clouds: Clouds,
    var dt: Long,
    var sys: Sys,
    var timezone: Long,
    var id: Long,
    var name: String,
    var cod: Long
)

data class Coord(var lon: Double, var lat: Double)

data class Weather(var id: Int, var main: String, var description: String, var icon: String)

data class Main(var temp: Double, var feels_like: Double,
                var temp_min: Double, var temp_max: Double,
                var pressure: Int, var humidity: Int,
                var sea_level: Int, var grnd_level: String)

data class Wind(var speed: Double, var deg: Long, var gust: Double)

data class Rain(var ih: Double)

data class Clouds(var all: Long)

data class Sys(var country: String, var sunrise: Long, var sunset: Long)


