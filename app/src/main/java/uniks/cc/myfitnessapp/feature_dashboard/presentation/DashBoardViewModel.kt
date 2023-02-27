package uniks.cc.myfitnessapp.feature_dashboard.presentation

import android.annotation.SuppressLint
import android.location.LocationManager
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.presentation.WorkoutEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val locationManager: LocationManager,
    private val connectivityManager: ConnectivityManager,
    private val fusedLocationProviderClient: FusedLocationProviderClient,

) : ViewModel() {
    private val _dashBoardState: DashBoardState = DashBoardState()
    val dashBoardState = mutableStateOf(_dashBoardState)

    var workouts : List<Workout> = mutableStateListOf()

    init {
        dashBoardState.value.selectedWorkoutDetail = null
        refreshWorkouts()
        dashBoardState.value = dashBoardState.value.copy(
            workouts = workouts,
            currentWorkout = coreRepository.currentWorkout
        )

    }

    fun refreshWorkouts() {
        workouts = getAllWorkoutsFromDatabase()
    }

    private fun getAllWorkoutsFromDatabase(): List<Workout> {
        lateinit var workouts: List<Workout>
        runBlocking { workouts = coreRepository.getAllWorkoutsFromDatabase() }
        return workouts.sortedByDescending { it.timeStamp }
    }

    fun onSportActivityDetailClick(workout: Workout) {
        coreRepository.selectedWorkoutDetail = workout
        coreRepository.onNavigationAction(NavigationEvent.OnWorkoutDetailClick(workout))
    }

    fun hasGpsSignal(): Boolean {
        return locationManager.isLocationEnabled
    }

    fun hasInternetConnection(): Boolean {
        return connectivityManager.activeNetwork != null
    }

    @SuppressLint("MissingPermission")
    fun setWeatherData() {
        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            viewModelScope.launch {
                location?.let {
                    dashBoardState.value.currentWeatherData =
                        coreRepository.getCurrentWeather(location.latitude, location.longitude)
                }
            }
        }
    }
}