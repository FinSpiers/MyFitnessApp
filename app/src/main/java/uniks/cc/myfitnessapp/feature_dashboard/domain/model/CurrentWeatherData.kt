package uniks.cc.myfitnessapp.feature_dashboard.domain.model

import uniks.cc.myfitnessapp.core.domain.util.WeatherIcon

data class CurrentWeatherData(
    val currentTemperature : Int,
    val iconId : WeatherIcon,
    val isWeatherGood : Boolean
)
