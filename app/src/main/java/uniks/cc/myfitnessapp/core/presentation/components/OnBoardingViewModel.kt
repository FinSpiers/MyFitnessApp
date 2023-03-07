package uniks.cc.myfitnessapp.core.presentation.components

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.repository.SensorRepository
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import uniks.cc.myfitnessapp.feature_settings.domain.model.Settings
import uniks.cc.myfitnessapp.feature_settings.domain.repository.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val coreRepository: CoreRepository,
    private val workoutRepository: WorkoutRepository,
    private val sensorRepository: SensorRepository
) : ViewModel() {
    lateinit var settings: Settings
    init {
        runBlocking {
            settings = settingsRepository.getSettingsFromDatabase()
            workoutRepository.oldStepsValue = sensorRepository.stepCounterSensorValueStateFlow.value
        }
    }

    fun navigate() {
        coreRepository.onNavigationAction(NavigationEvent.OnDashBoardClick)
        viewModelScope.launch {
            settings.onBoardingShown = true
            settingsRepository.saveSettingsToDatabase(settings)
        }
    }

    fun checkPermissions(context : Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACTIVITY_RECOGNITION), ActivityCompat.RECEIVER_VISIBLE_TO_INSTANT_APPS)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        ActivityCompat.OnRequestPermissionsResultCallback { requestCode, permissions, grantResults ->
            for (i in 0..grantResults.size) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("PERMISSION", "Granted permission ${permissions[i]}")
                }
                else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    Log.e("PERMISSION", "Denied permission ${permissions[i]}")
                }

            }
        }
    }
}