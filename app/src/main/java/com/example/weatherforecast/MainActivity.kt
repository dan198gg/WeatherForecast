package com.example.weatherforecast

import android.os.Bundle
import android.text.Layout.Alignment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import java.net.InetSocketAddress
import java.net.Socket

class MainActivity : ComponentActivity() {
    val REQUEST_URL = "https://api.openweathermap.org/data/2.5/"
    var temp by mutableStateOf<String>("")
    var testLat by mutableStateOf(43.07)
    var testLon by mutableStateOf(131.54)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column {
                TextFie1ld()
                Text(text = temp)
        }
            }
    }

    fun getWeatherData(){

        val request = Request.Builder()
            .url(REQUEST_URL + "weather?lat=$testLat&lon=$testLon&appid=${Untracked.API_KEY}&units=metric")
            .build()
        ClientSingleTon.OK_HTTP_CLIENT.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                Log.e("ONFAULURE", e.message.toString())
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Log.e("RESP",response.code.toString())
                    Log.e("IODANIIL", "IOEX")
                    throw IOException()
                }
                val jsonData = response.body.string()
                Log.i("RESPONSEBODY", jsonData)
                try {
                    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    val jsonAdapter = moshi.adapter(WeatherData::class.java)
                    val weatherData = jsonAdapter.fromJson(jsonData)
                    Log.i("TEMP", weatherData!!.main.temp.toString())
                    temp = weatherData!!.main.temp.toString()
                } catch (ex: Exception) {
                    Log.e("JSONCONVERTERROR", ex.message.toString())
                }

            }

        })
    }
    @Composable
    fun TextFie1ld() {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center){
            Column {
            TextField(value = testLat.toString(), onValueChange = { testLat = it.toDouble() })
            TextField(value = testLon.toString(), onValueChange = { testLon = it.toDouble() }, modifier = Modifier.padding(0.dp,30.dp))
                Button(onClick = { getWeatherData() }, modifier = Modifier.padding(0.dp,30.dp)) {
                    Text(text = "Посмотреть погоду")
                }
            }
        }
    }
}


