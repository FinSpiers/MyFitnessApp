package uniks.cc.myfitnessapp.core.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.util.Screen
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val coreRepository: CoreRepository
) : ViewModel() {
    private lateinit var navController: NavHostController

    private val navDestinations = listOf(
        Screen.DashBoardScreen.route,
        Screen.SettingsScreen.route,
        Screen.ActivityScreenTypeA.route,
        Screen.ActivityScreenTypeB.route,
        Screen.ActivityDetailScreenTypeA.route,
        Screen.ActivityDetailScreenTypeB.route
    )

    private val bottomNavDestinations = listOf(
        Screen.DashBoardScreen.route,
        Screen.SettingsScreen.route,
    )

    var navBarState = mutableStateOf(NavigationBarState())

    init {
        coreRepository.navigate = this::onEvent
    }

    fun onEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.OnDashBoardClicked -> navigate(Screen.DashBoardScreen.route)
            is NavigationEvent.OnSettingsClick -> navigate(Screen.SettingsScreen.route)

            is NavigationEvent.OnStartWorkOut -> {
                when (event.workoutId) {
                    in 0..2 -> navigate(Screen.ActivityScreenTypeA.route)
                    in 3..5 -> navigate(Screen.ActivityScreenTypeB.route)
                }
            }
            is NavigationEvent.OnWorkoutDetailClick -> {
                when (event.sportActivity.workoutId) {
                    in 0..2 -> navigate(Screen.ActivityDetailScreenTypeA.route)
                    in 3..5 -> navigate(Screen.ActivityDetailScreenTypeB.route)
                }
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