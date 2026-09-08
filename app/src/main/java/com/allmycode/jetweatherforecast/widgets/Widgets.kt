package com.allmycode.jetweatherforecast.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.allmycode.jetweatherforecast.R
import com.allmycode.jetweatherforecast.model.WeatherItem
import com.allmycode.jetweatherforecast.utils.formatDateDay
import com.allmycode.jetweatherforecast.utils.formatDateTime
import com.allmycode.jetweatherforecast.utils.formatDecimals


@Composable
fun ThisWeekForecast(weatherList: List<WeatherItem>) {
    LazyColumn {
        items(weatherList) { weatherItem: WeatherItem ->
            val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
            Row(modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = formatDateDay(weatherItem.dt))
                WeatherStateImage(imageUrl = imageUrl)
                Surface(modifier = Modifier.padding(4.dp),
                    shape = CircleShape,
                    color = Color(0xFFFFC400)) {

                    Text(weatherItem.weather[0].description, modifier = Modifier.padding(4.dp), style = MaterialTheme.typography.bodyMedium)
                }
                Text(text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color.Blue.copy(0.7f), fontWeight = FontWeight.SemiBold)) {
                        append(text = formatDecimals(weatherItem.temp.max) + "°")
                    }
                    withStyle(style = SpanStyle(color = Color.Gray, fontWeight = FontWeight.SemiBold)) {
                        append(text = formatDecimals(weatherItem.temp.min) + "°")
                    }
                })
            }

        }

    }
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity), contentDescription = "humidity icon",
                modifier = Modifier.size(25.dp))
            Text(text = "${weather.humidity} %")
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.pressure), contentDescription = "pressure icon",
                modifier = Modifier.size(25.dp))
            Text(text = "${weather.pressure} psi")
        }

        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.wind), contentDescription = "wind icon",
                modifier = Modifier.size(25.dp))
            Text(text = "${weather.speed} km/h")
        }
    }
}

@Composable
fun SunsetSunriseRow(weather: WeatherItem) {
    Row(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.sunrise), contentDescription = "sunrise icon",
                modifier = Modifier.size(25.dp))
            Text(text = formatDateTime(weather.sunrise))
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.sunset), contentDescription = "sunset icon",
                modifier = Modifier.size(25.dp))
            Text(text = formatDateTime(weather.sunset))
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    AsyncImage(model = imageUrl, contentDescription = "Weather image", modifier = Modifier.size(80.dp))
}

