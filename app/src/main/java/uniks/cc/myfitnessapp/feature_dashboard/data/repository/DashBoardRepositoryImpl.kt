package uniks.cc.myfitnessapp.feature_dashboard.data.repository

import uniks.cc.myfitnessapp.feature_dashboard.data.network.OpenWeatherApiService
import uniks.cc.myfitnessapp.feature_dashboard.data.network.response.toCurrentWeatherData
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.DashBoardRepository

class DashBoardRepositoryImpl(val apiService: OpenWeatherApiService) : DashBoardRepository {
    override suspend fun getCurrentWeatherData(lat: Double, lon: Double): CurrentWeatherData {
        return apiService.getCurrentWeatherAsync(lat, lon, "metric", "en").await().toCurrentWeatherData()
    }
}