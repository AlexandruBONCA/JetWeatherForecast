package com.allmycode.jetweatherforecast.screens.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.allmycode.jetweatherforecast.data.DataOrException
import com.allmycode.jetweatherforecast.model.Weather
import com.allmycode.jetweatherforecast.navigation.WeatherScreens
import com.allmycode.jetweatherforecast.utils.formatDate
import com.allmycode.jetweatherforecast.utils.formatDecimals
import com.allmycode.jetweatherforecast.widgets.*

@Composable
fun MainScreen(navController: NavController,
               mainViewModel: MainViewModel = hiltViewModel(),
               city: String?) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)) {
        value = mainViewModel.getWeatherData(city.toString())
    }.value


    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weatherData.data!!, navController)
    }


}

@Composable
fun MainScaffold(weather: Weather, navController: NavController) {

    Scaffold(
        topBar = {
            WeatherAppBar(title = weather.city.name + "," + weather.city.country,
//                icon = Icons.Default.ArrowBack,
                navController = navController,
                onAddActionClicked = {
                    navController.navigate(WeatherScreens.SearchScreen.name)
                },
                onButtonClicked = { Log.d("Button", "button clicked!")})
        }
    ) { innerPadding ->
       MainContent(data = weather,
           innerPadding = innerPadding)
    }
}

@Composable
fun MainContent(data: Weather, innerPadding: PaddingValues) {

    val imageUrl = "https://openweathermap.org/img/wn/${data.list[0].weather[0].icon}.png"

    Column(        modifier = Modifier
        .padding(innerPadding)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {


        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = formatDate(data.list[0].dt),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(6.dp)
                )
                //Image
                WeatherStateImage(imageUrl = imageUrl)
                Text(text = formatDecimals(data.list[0].temp.day) + "°", style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold)
                Text(text = data.list[0].weather[0].main, fontStyle = FontStyle.Italic)

            }
        }
        HumidityWindPressureRow(data.list[0])
        SunsetSunriseRow(data.list[0])
        Box(modifier = Modifier.size(15.dp))
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "This Week", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold))
        }
        Box(modifier = Modifier.size(15.dp))
        ThisWeekForecast(data.list)
    }
}

