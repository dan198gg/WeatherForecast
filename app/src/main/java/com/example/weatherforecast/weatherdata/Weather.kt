package com.example.weatherforecast.weatherdata

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)