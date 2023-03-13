package uniks.cc.myfitnessapp.feature_dashboard.data.repository

import android.util.Log
import uniks.cc.myfitnessapp.core.data.database.MyFitnessDatabase
import uniks.cc.myfitnessapp.core.domain.model.Steps
import uniks.cc.myfitnessapp.feature_dashboard.data.DashboardDao
import uniks.cc.myfitnessapp.feature_dashboard.data.network.OpenWeatherApiService
import uniks.cc.myfitnessapp.feature_dashboard.data.network.response.toCurrentWeatherData
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.DashBoardRepository

class DashBoardRepositoryImpl(private val apiService: OpenWeatherApiService, val dashboardDao: DashboardDao) : DashBoardRepository {
    override var oldStepsValue: Int = 0

    override suspend fun getCurrentWeatherData(lat: Double, lon: Double): CurrentWeatherData {
        return apiService.getCurrentWeatherAsync(lat, lon, "metric", "en").await().toCurrentWeatherData()
    }

    override suspend fun saveDailySteps(steps: Steps) {
        dashboardDao.saveDailySteps(steps)
    }

    override suspend fun getDailyStepsByDate(date: String): Steps? {
        return dashboardDao.getDailyStepsByDate(date)
    }

    override suspend fun getAllDailySteps(): List<Steps> {
        return dashboardDao.getAllDailySteps()
    }
}