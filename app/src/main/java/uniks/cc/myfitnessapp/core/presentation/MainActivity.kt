package uniks.cc.myfitnessapp.core.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.util.hasActivityRecognitionPermission
import uniks.cc.myfitnessapp.core.presentation.navigation.NavigationHost
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.BottomNavigationBar
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {
    @Inject
    lateinit var workoutRepository: WorkoutRepository
    @Inject
    lateinit var coreRepository: CoreRepository

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val navController = rememberNavController()
            viewModel.setNavController(navController)
            viewModel.setRepoContext(this)

            val navBarState = viewModel.navBarState.value

            MyFitnessAppTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            navBarState = navBarState,
                            onEvent = viewModel::onNavigationEvent
                        )
                    },
                ) {
                    NavigationHost(
                        navController = navController,
                        startDestination = navBarState.currentRoute
                    )
                }
            }
        }
    }
}