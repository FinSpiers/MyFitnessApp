package uniks.cc.myfitnessapp.feature_dashboard.presentation

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_active_workout.domain.repository.WorkoutRepository
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val workoutRepository: WorkoutRepository,
    private val locationManager: LocationManager,
    private val connectivityManager: ConnectivityManager,
    private val fusedLocationProviderClient: FusedLocationProviderClient,

) : ViewModel() {
    val dashBoardState = mutableStateOf(DashBoardState())

    init {
        val sportActivities: List<Workout> = listOf(
            Workout("Walking", Instant.now().epochSecond - 2312, 2.5, 1022, 10.4, 5.0, 5.4, null),
            Workout("Running", Instant.now().epochSecond - 1231212, 0.5, 174, 0.4, 14.0, 7.7, null),
            Workout("PushUps", Instant.now().epochSecond - 143332122, 2.2, 674, null, null, null, 500),
            Workout("Squats", Instant.now().epochSecond - 999922, 9.2, 56742, null, null, null, 712)
        )
        dashBoardState.value.workouts.addAll(sportActivities)
        dashBoardState.value.currentWorkout = dashBoardState.value.workouts[0]
    }

    fun onSportActivityDetailClick(workout: Workout) {
        coreRepository.navigate(NavigationEvent.OnWorkoutDetailClick(workout))
        workoutRepository.selectedWorkoutDetail = workout
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