package uniks.cc.myfitnessapp.core.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.util.Screen
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarState
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val coreRepository: CoreRepository,
    private val workoutRepository: WorkoutRepository
) : ViewModel() {
    private lateinit var navController: NavHostController

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
        coreRepository.navBarState = navBarState.value
    }
    fun setRepoContext(context : Context) {
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
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
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