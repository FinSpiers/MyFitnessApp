package uniks.cc.myfitnessapp.core.domain.repository

import androidx.navigation.NavHostController
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData

interface CoreRepository {
    var navController : NavHostController?
    val navDestinations : List<String>
    var isLocationPermissionGranted : Boolean

    suspend fun setNavController(navController : NavHostController) {
        this.navController = navController
    }

    fun navigate(destination : String)

    fun setLocationPermissionGranted()

    suspend fun getAllSportActivitiesFromDatabase() : List<SportActivity>

    suspend fun addSportActivityToDatabase(sportActivity: SportActivity)

    suspend fun getSportActivityById(id : Int) : SportActivity?

    suspend fun getCurrentWeather(lat : Double, lon : Double, unit : String, language : String) : CurrentWeatherData


}