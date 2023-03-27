package uniks.cc.myfitnessapp.core.domain.util

import androidx.annotation.DrawableRes
import uniks.cc.myfitnessapp.R

enum class WeatherIcon(@DrawableRes resId: Int) {
    Clouds(R.drawable.image_weather_clouds),
    Rain(R.drawable.image_weather_rain),
    Snow(R.drawable.image_weather_snow),
    Extreme(R.drawable.image_weather_extreme),
    Sun(R.drawable.image_weather_sunny),
    Fog(R.drawable.image_weather_fog),
    Mist(R.drawable.image_weather_fog),
    Clear(R.drawable.image_weather_sunny),
    Drizzle(R.drawable.image_weather_drizzle),
    Unknown(R.drawable.image_weather_extreme),
}

