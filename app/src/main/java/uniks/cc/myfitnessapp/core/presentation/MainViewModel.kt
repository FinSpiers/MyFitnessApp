package uniks.cc.myfitnessapp.core.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.location.ActivityRecognitionClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.util.ActivityTransitionReceiver
import uniks.cc.myfitnessapp.core.domain.util.ActivityTransitions
import uniks.cc.myfitnessapp.core.domain.util.Screen
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarState
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import uniks.cc.myfitnessapp.feature_settings.domain.model.Settings
import uniks.cc.myfitnessapp.feature_settings.domain.repository.SettingsRepository
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.S)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val workoutRepository: WorkoutRepository,
    private val settingsRepository: SettingsRepository,
    private val activityRecognitionClient: ActivityRecognitionClient
) : ViewModel() {
    private lateinit var navController: NavHostController
    private lateinit var settings: Settings

    private val navDestinations = listOf(
        Screen.DashBoardScreen.route,
        Screen.SettingsScreen.route,
        Screen.CurrentWorkoutScreen.route,
        Screen.ActivityDetailScreen.route
    )

    private val bottomNavDestinations = listOf(
        Screen.DashBoardScreen.route,
        Screen.SettingsScreen.route
    )

    var navBarState = mutableStateOf(NavigationBarState())

    init {
        coreRepository.onNavigationAction = this::onNavigationEvent
        runBlocking {
            settings = settingsRepository.getSettingsFromDatabase()
        }
        coreRepository.checkActivityRecognitionPermission()
        if (coreRepository.isActivityRecognitionPermissionGranted) {
            requestForUpdates()
        }
        coreRepository.navBarState = navBarState.value

    }


    @SuppressLint("MissingPermission")
    private fun requestForUpdates() {
        activityRecognitionClient
            .requestActivityTransitionUpdates(
                ActivityTransitions.getActivityTransitionRequest(),
                getPendingIntent()
            )
            .addOnSuccessListener {
                Log.e("ATU", "successful registration")
            }
            .addOnFailureListener {
                Log.e("ATU", "Unsuccessful registration")
            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(coreRepository.context, ActivityTransitionReceiver::class.java)
        return PendingIntent.getBroadcast(
            coreRepository.context,
            122,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
    }

    fun setRepoContext(context: Context) {
        coreRepository.context = context
    }

    fun onNavigationEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.OnDashBoardClick -> navigate(Screen.DashBoardScreen.route)
            is NavigationEvent.OnSettingsClick -> navigate(Screen.SettingsScreen.route)
            is NavigationEvent.OnStartWorkoutClick -> navigate(Screen.CurrentWorkoutScreen.route)
            is NavigationEvent.OnWorkoutDetailClick -> {
                if (workoutRepository.currentWorkout == event.workout) {
                    navigate(Screen.CurrentWorkoutScreen.route)
                } else {
                    navigate(Screen.ActivityDetailScreen.route)
                }
            }
            is NavigationEvent.OnStopWorkoutClick -> navigate(Screen.DashBoardScreen.route)
            is NavigationEvent.OnOpenAppSettingsClick -> {
                val openAppSettingsIntent = Intent().apply {
                    action = ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", coreRepository.context.packageName, null)
                }
                coreRepository.context.startActivity(openAppSettingsIntent)
            }
        }
    }

    private fun navigate(destination: String) {
        if (destination in navDestinations) {
            if (destination in bottomNavDestinations) {
                navController.navigate(destination) {
                    launchSingleTop = true
                    restoreState = true
                    popUpTo(destination)
                }
                navBarState.value = navBarState.value.copy(
                    currentRoute = destination,
                    subRoute = null
                )
            } else {
                navController.navigate(destination)
                navBarState.value = navBarState.value.copy(subRoute = destination)
            }
        }
    }

    fun setNavController(navController: NavHostController) {
        this.navController = navController
    }
}