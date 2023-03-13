package uniks.cc.myfitnessapp.feature_dashboard.domain.repository


import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData

interface DashBoardRepository {

    suspend fun getCurrentWeatherData(lat : Double, lon : Double) : CurrentWeatherData
}