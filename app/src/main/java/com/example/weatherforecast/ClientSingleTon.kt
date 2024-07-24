package com.example.weatherforecast

import okhttp3.OkHttpClient

class ClientSingleTon {
    companion object{
        val OK_HTTP_CLIENT=OkHttpClient()
    }
}