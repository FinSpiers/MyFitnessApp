package uniks.cc.myfitnessapp.core.domain.util

import androidx.annotation.DrawableRes
import uniks.cc.myfitnessapp.R

enum class WeatherIcon(@DrawableRes resId : Int) {
    CLOUDS(R.drawable.image_weather_clouds),
    RAIN(R.drawable.image_weather_rain),
    SNOW(R.drawable.image_weather_snow),
    EXTREME(R.drawable.image_weather_extreme),
    SUN(R.drawable.image_weather_sunny),
    FOG(R.drawable.image_weather_fog),
    CLEAR(R.drawable.image_weather_sunny),
    DRIZZLE(R.drawable.image_weather_drizzle),
    UNKNOWN(R.drawable.image_weather_extreme);
}

fun getIcon(apiName : String) : WeatherIcon {
    return when(apiName) {
        "Clouds" -> WeatherIcon.CLOUDS
        "Rain" -> WeatherIcon.RAIN
        "Snow" -> WeatherIcon.SNOW
        "Extreme" -> WeatherIcon.EXTREME
        "Sun" -> WeatherIcon.SUN
        "Fog" -> WeatherIcon.FOG
        "Clear" -> WeatherIcon.CLEAR
        "Drizzle" -> WeatherIcon.DRIZZLE
        else -> WeatherIcon.UNKNOWN
    }
}

