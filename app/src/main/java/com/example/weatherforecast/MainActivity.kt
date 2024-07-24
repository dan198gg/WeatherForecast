package com.example.weatherforecast

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherforecast.supersecurityprivatesafe.Untracked
import com.example.weatherforecast.ui.theme.WeatherForecastTheme
import com.example.weatherforecast.weatherdata.WeatherData
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class MainActivity : ComponentActivity() {
    val REQUEST_URL="https://api.openweathermap.org/data/2.5/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            getWeatherData()
        }
    }
    fun getWeatherData(){
        val testLat=43.07
        val testLon=131.54
        val request=Request.Builder()
            .url(REQUEST_URL+"weather?lat=$testLat&lon=$testLon&appid=${Untracked.API_KEY}&units=metric").build()
        ClientSingleTon.OK_HTTP_CLIENT.newCall(request).enqueue(object:Callback {
            override fun onFailure(call: Call, e: IOException) {
               Log.e("ONFAULURE",e.message.toString())
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful){
                    Log.e("IODANIIL","IOEX")
                    throw IOException()
                }
                val jsonData=response.body.string()
                Log.i("RESPONSEBODY",jsonData)
                try {
                    val moshi= Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter=moshi.adapter(WeatherData::class.java)
                    val weatherData=jsonAdapter.fromJson(jsonData)
                    Log.i("TEMP",weatherData!!.main.temp.toString())
                }
                catch (ex:Exception){
                    Log.e("JSONCONVERTERROR","ERROR")
                }
            }

        })
    }
}

