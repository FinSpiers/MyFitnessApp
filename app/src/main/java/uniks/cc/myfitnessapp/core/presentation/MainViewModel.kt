package uniks.cc.myfitnessapp.core.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val bottomNavDestinations : List<String>
) : ViewModel() {
    private lateinit var navController : NavHostController
    var navBarState = mutableStateOf(NavigationBarState(bottomNavDestinations[0]))

    fun onEvent(event : NavigationBarEvent) {
        when(event) {
            is NavigationBarEvent.OnDashBoardPressed -> navigate(bottomNavDestinations[0])
            is NavigationBarEvent.OnSettingsPressed -> navigate(bottomNavDestinations[1])
        }
    }

    private fun navigate(destination : String) {
        if (destination in bottomNavDestinations) {
            navController.navigate(destination) {
                launchSingleTop = true
                restoreState = true
                popUpTo(destination)
            }
            navBarState.value = navBarState.value.copy(currentRoute = destination)
        }
    }

    fun setNavController(navController: NavHostController) {
        this.navController = navController
    }
}