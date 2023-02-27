package uniks.cc.myfitnessapp.core.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.presentation.navigation.NavigationHost
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.BottomNavigationBar
import uniks.cc.myfitnessapp.feature_dashboard.presentation.components.WorkoutFab
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {

    @Inject lateinit var coreRepository: CoreRepository
    @Inject lateinit var snackbarHostState: SnackbarHostState
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val navController = rememberNavController()
            viewModel.setNavController(navController)
            val navBarState = viewModel.navBarState.value

            MyFitnessAppTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            navBarState = navBarState,
                            onEvent = viewModel::onNavigationEvent
                        )
                    },
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
                    floatingActionButton = { WorkoutFab(navBarState, coreRepository::onNavigationAction.get(), coreRepository::onWorkoutAction.get()) }
                ) {it.calculateBottomPadding()
                    NavigationHost(
                        navController = navController,
                        startDestination = navBarState.currentRoute
                    )
                    //GlobalScope.launch {
                    //    snackbarHostState.showSnackbar("Test", "OK", false, SnackbarDuration.Long)
                    //}
                }
            }
        }
    }
}