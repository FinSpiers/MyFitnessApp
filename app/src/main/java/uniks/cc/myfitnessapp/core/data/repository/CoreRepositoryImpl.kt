package uniks.cc.myfitnessapp.core.data.repository

import uniks.cc.myfitnessapp.core.data.network.response.toCurrentWeatherData
import uniks.cc.myfitnessapp.core.data.network.OpenWeatherApiService
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData
import kotlin.reflect.KFunction1

class CoreRepositoryImpl(
    private val weatherApiService: OpenWeatherApiService
) : CoreRepository {
    override var isLocationPermissionGranted: Boolean = false
    override lateinit var navigate: KFunction1<NavigationEvent, Unit>

    override fun setLocationPermissionGranted() {
        isLocationPermissionGranted = true
    }

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double,
        unit: String,
        language: String
    ): CurrentWeatherData {
        return weatherApiService.getCurrentWeatherAsync(lat, lon, unit, language).await()
            .toCurrentWeatherData()
    }

}
