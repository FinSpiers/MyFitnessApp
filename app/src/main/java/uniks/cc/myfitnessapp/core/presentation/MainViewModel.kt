package uniks.cc.myfitnessapp.core.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navDestinations : List<String>,
    private val coreRepository: CoreRepository
) : ViewModel() {
    private lateinit var navController : NavHostController
    var navBarState = mutableStateOf(NavigationBarState(navDestinations[0]))

    fun onEvent(event : NavigationEvent) {
        when(event) {
            is NavigationEvent.OnDashBoardPressed -> coreRepository.navigate(navDestinations[0])
            is NavigationEvent.OnSettingsPressed -> coreRepository.navigate(navDestinations[1])

        }
    }

    private fun navigate(destination : String) {
        if (destination in navDestinations) {
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