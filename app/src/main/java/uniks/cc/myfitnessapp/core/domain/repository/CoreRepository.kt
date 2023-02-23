package uniks.cc.myfitnessapp.core.domain.repository

import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData
import kotlin.reflect.KFunction1

interface CoreRepository {
    var isLocationPermissionGranted : Boolean
    var navigate : KFunction1<NavigationEvent, Unit>
    fun setLocationPermissionGranted()

    suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        language: String
    ): CurrentWeatherData


}