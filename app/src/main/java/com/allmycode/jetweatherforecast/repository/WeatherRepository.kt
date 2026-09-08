package com.allmycode.jetweatherforecast.repository

import android.util.Log
import com.allmycode.jetweatherforecast.data.DataOrException
import com.allmycode.jetweatherforecast.model.Weather
import com.allmycode.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(cityQuery)
        } catch (e: Exception) {
            Log.d("REX", "getWeather: $e")
            return DataOrException(e = e)
        }
        Log.d("OK PLAYLOAD", "getWeather: $response")
        return DataOrException(data = response)
    }
}