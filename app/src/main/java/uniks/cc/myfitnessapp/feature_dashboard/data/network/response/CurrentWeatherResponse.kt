package uniks.cc.myfitnessapp.feature_dashboard.data.network.response

import com.example.myweather.feature_weather.data.data_source.network.response.Coord
import com.example.myweather.feature_weather.data.data_source.network.response.Main
import com.example.myweather.feature_weather.data.data_source.network.response.Weather
import com.example.myweather.feature_weather.data.data_source.network.response.Wind
import com.google.gson.annotations.SerializedName
import uniks.cc.myfitnessapp.core.domain.util.WeatherIcon
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData
import kotlin.math.roundToInt

data class CurrentWeatherResponse(
    val coord: Coord,
    val cod: Int,
    val dt: Int,
    val main: Main,

    @SerializedName("name")
    val locationName: String,
    val weather: List<Weather>,
    val wind: Wind,
    val timezone : Int
)

fun CurrentWeatherResponse.toCurrentWeatherData() : CurrentWeatherData {
    return CurrentWeatherData(
        currentTemperature = main.temp.roundToInt(),
        isWeatherGood = (weather[0].main != "Rain" && weather[0].main != "Snow") && main.temp.roundToInt() in 0..30,
        iconId = WeatherIcon.valueOf(weather[0].main)
    )
}