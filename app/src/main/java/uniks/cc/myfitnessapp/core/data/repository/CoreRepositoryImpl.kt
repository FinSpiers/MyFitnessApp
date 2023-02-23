package uniks.cc.myfitnessapp.core.data.repository

import uniks.cc.myfitnessapp.core.data.network.response.toCurrentWeatherData
import uniks.cc.myfitnessapp.core.data.database.SportActivitiesDao
import uniks.cc.myfitnessapp.core.data.network.OpenWeatherApiService
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData
import kotlin.reflect.KFunction1

class CoreRepositoryImpl(
    private val sportActivitiesDao: SportActivitiesDao,
    private val weatherApiService: OpenWeatherApiService
) : CoreRepository {
    override var isLocationPermissionGranted: Boolean = false
    override lateinit var navigate: KFunction1<NavigationEvent, Unit>

    fun setNavigation(function : KFunction1<NavigationEvent, Unit>) {
        navigate = function
    }

    override fun setLocationPermissionGranted() {
        TODO("Not yet implemented")
    }

    override suspend fun getAllSportActivitiesFromDatabase(): List<SportActivity> {
        TODO("Not yet implemented")
    }

    override suspend fun addSportActivityToDatabase(sportActivity: SportActivity) {
        TODO("Not yet implemented")
    }

    override suspend fun getSportActivityById(id: Int): SportActivity? {
        TODO("Not yet implemented")
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