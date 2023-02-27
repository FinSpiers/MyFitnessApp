package uniks.cc.myfitnessapp.core.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.util.Screen
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarState
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val coreRepository: CoreRepository
) : ViewModel() {
    private lateinit var navController: NavHostController

    private val navDestinations = listOf(
        Screen.DashBoardScreen.route,
        Screen.SettingsScreen.route,
        Screen.CurrentActivityScreen.route,
        Screen.ActivityDetailScreen.route
    )

    private val bottomNavDestinations = listOf(
        Screen.DashBoardScreen.route,
        Screen.SettingsScreen.route
    )

    var navBarState = mutableStateOf(NavigationBarState())

    init {
        coreRepository.onNavigationAction = this::onNavigationEvent
        coreRepository.onWorkoutAction = this::onWorkoutEvent
    }

    fun onNavigationEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.OnDashBoardClick -> navigate(Screen.DashBoardScreen.route)
            is NavigationEvent.OnSettingsClick -> navigate(Screen.SettingsScreen.route)
            is NavigationEvent.OnStartWorkoutClick -> navigate(Screen.CurrentActivityScreen.route)
            is NavigationEvent.OnWorkoutDetailClick -> navigate(Screen.ActivityDetailScreen.route)
            is NavigationEvent.OnStopWorkoutClick -> navigate(Screen.DashBoardScreen.route)
        }
    }

    private fun onWorkoutEvent(event : WorkoutEvent) {
        when(event) {
            is WorkoutEvent.StartWorkout -> {
                val currentWorkout = Workout(
                    workoutName = event.workoutName,
                    timeStamp = Instant.now().epochSecond,
                    duration = 0.0,
                    kcal = 0
                ).apply {
                    if(workoutName in listOf("Walking", "Running", "Bicycling")) {
                        distance = 0.0
                        pace = 0.0
                        avgPace = 0.0
                        // TODO: use activity recognition api and a backgroundService to
                        // TODO: track users activities,time spend, calculate distance, pace, burned kcal, etc..
                    }
                    else {
                        repetitions = 0
                        // TODO: Use backgroundService that tracks the time and repetitions for the activity
                    }
                }
                coreRepository.currentWorkout = currentWorkout
                viewModelScope.launch {
                    coreRepository.addWorkoutToDatabase(currentWorkout)
                }


            }
            is WorkoutEvent.StopWorkout -> {
                coreRepository.currentWorkout = null
                /* TODO */
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