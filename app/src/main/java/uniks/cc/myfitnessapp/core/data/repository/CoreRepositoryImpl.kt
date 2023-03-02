package uniks.cc.myfitnessapp.core.data.repository

import android.content.Context
import uniks.cc.myfitnessapp.core.data.network.response.toCurrentWeatherData
import uniks.cc.myfitnessapp.core.data.network.OpenWeatherApiService
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarState
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData
import kotlin.reflect.KFunction1

class CoreRepositoryImpl(
    private val weatherApiService: OpenWeatherApiService
) : CoreRepository {
    override var navBarState: NavigationBarState? = null
    override var isLocationPermissionGranted: Boolean = false
    override lateinit var onNavigationAction: KFunction1<NavigationEvent, Unit>
    override lateinit var context: Context
    init {

    }

    override fun setLocationPermissionGranted() {
        isLocationPermissionGranted = true
    }

    override suspend fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): CurrentWeatherData {
        return weatherApiService.getCurrentWeatherAsync(lat, lon, "metric", "en").await()
            .toCurrentWeatherData()
    }
}
