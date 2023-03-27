package uniks.cc.myfitnessapp.feature_dashboard.data.repository

import uniks.cc.myfitnessapp.core.domain.model.Steps
import uniks.cc.myfitnessapp.core.domain.util.TimestampConverter
import uniks.cc.myfitnessapp.feature_dashboard.data.DashboardDao
import uniks.cc.myfitnessapp.feature_dashboard.data.network.OpenWeatherApiService
import uniks.cc.myfitnessapp.feature_dashboard.data.network.response.toCurrentWeatherData
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.DashBoardRepository
import java.time.Instant

class DashBoardRepositoryImpl(
    private val apiService: OpenWeatherApiService,
    private val dashboardDao: DashboardDao
) : DashBoardRepository {

    override suspend fun getOldStepsValueFromDatabase(): Long {
        val steps =
            dashboardDao.getDailyStepsByDate(TimestampConverter.convertToDate(Instant.now().epochSecond))
        return steps?.sensorCount ?: -1
    }

    override suspend fun getCurrentWeatherData(lat: Double, lon: Double): CurrentWeatherData {
        return apiService.getCurrentWeatherAsync(lat, lon, "metric", "en").await()
            .toCurrentWeatherData()
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