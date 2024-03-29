package uniks.cc.myfitnessapp.feature_dashboard.domain.repository


import uniks.cc.myfitnessapp.feature_dashboard.domain.model.Steps
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData

interface DashBoardRepository {
    suspend fun getOldStepsValueFromDatabase(): Long

    suspend fun getCurrentWeatherData(lat: Double, lon: Double): CurrentWeatherData

    suspend fun saveDailySteps(steps: Steps)

    suspend fun getDailyStepsByDate(date: String): Steps?

    suspend fun getLastSevenDailyStepsValues() : List<Steps>

    suspend fun getAllDailySteps(): List<Steps>
}