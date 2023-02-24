package uniks.cc.myfitnessapp.feature_dashboard.domain.model

import uniks.cc.myfitnessapp.core.domain.util.WeatherIcon

data class CurrentWeatherData(
    val currentTemperature : Int = 0,
    val iconId : WeatherIcon = WeatherIcon.CLEAR,
    val isWeatherGood : Boolean = true,
    val currentWeatherMain : String = "Clear"
)
