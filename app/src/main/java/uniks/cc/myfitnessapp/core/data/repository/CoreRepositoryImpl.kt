package uniks.cc.myfitnessapp.core.data.repository

import androidx.navigation.NavHostController
import uniks.cc.myfitnessapp.core.data.network.response.toCurrentWeatherData
import uniks.cc.myfitnessapp.core.data.database.SportActivitiesDao
import uniks.cc.myfitnessapp.core.data.network.OpenWeatherApiService
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData

class CoreRepositoryImpl(
    private val sportActivitiesDao: SportActivitiesDao,
    private val weatherApiService: OpenWeatherApiService
) : CoreRepository {
    override var navController: NavHostController? = null
    override var isLocationPermissionGranted: Boolean = false
    override val navDestinations: List<String> = emptyList()

    override suspend fun setNavController(navController : NavHostController) {
        this.navController = navController
    }
    override suspend fun getAllSportActivitiesFromDatabase(): List<SportActivity> {
        return emptyList()
    }

    override suspend fun addSportActivityToDatabase(sportActivity: SportActivity) {
        TODO("Not yet implemented")
    }

    override suspend fun getSportActivityById(id: Int): SportActivity? {
        return null
    }

    override fun navigate(destination: String) {
        TODO("Not yet implemented")
    }

    override fun setLocationPermissionGranted() {
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