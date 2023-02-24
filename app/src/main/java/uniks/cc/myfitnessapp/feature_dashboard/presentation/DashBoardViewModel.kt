package uniks.cc.myfitnessapp.feature_dashboard.presentation

import android.annotation.SuppressLint
import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val locationManager: LocationManager,
    private val connectivityManager: ConnectivityManager,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
   val dashBoardState: DashBoardState

) : ViewModel() {

    init {
        dashBoardState.selectedWorkoutDetail = null
        viewModelScope.launch {
            dashBoardState.workouts.clear()
            dashBoardState.workouts.addAll(coreRepository.getAllWorkoutsFromDatabase())
        }
    }

    fun onSportActivityDetailClick(workout: Workout) {
        coreRepository.onNavigationAction(NavigationEvent.OnWorkoutDetailClick(workout))
    }

    private fun checkGpsState(): Boolean {
        return locationManager.isLocationEnabled
    }

    private fun checkInternetState(): Boolean {
        return connectivityManager.activeNetwork != null
    }

    @SuppressLint("MissingPermission")
    private fun setWeatherData() {
        if (checkInternetState()) {
            if (checkGpsState()) {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    viewModelScope.launch {
                        val currentWeatherData =
                            coreRepository.getCurrentWeather(location.latitude, location.longitude)

                    }

                }
            }
        }
    }

}