package uniks.cc.myfitnessapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uniks.cc.myfitnessapp.core.domain.util.Screen
import uniks.cc.myfitnessapp.feature_dashboard.presentation.DashBoardScreen
import uniks.cc.myfitnessapp.feature_settings.presentation.SettingsScreen
import uniks.cc.myfitnessapp.feature_workout.presentation.WorkoutDetailScreenTypeA
import uniks.cc.myfitnessapp.feature_workout.presentation.WorkoutDetailScreenTypeB
import uniks.cc.myfitnessapp.feature_workout.presentation.WorkoutScreenTypeA
import uniks.cc.myfitnessapp.feature_workout.presentation.WorkoutScreenTypeB

@Composable
fun NavigationHost(navController : NavHostController, startDestination : String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.DashBoardScreen.route) { DashBoardScreen() }
        composable(Screen.SettingsScreen.route) { SettingsScreen() }
        composable(Screen.ActivityScreenTypeA.route) { WorkoutScreenTypeA() }
        composable(Screen.ActivityScreenTypeB.route) { WorkoutScreenTypeB() }
        composable(Screen.ActivityDetailScreenTypeA.route) { WorkoutDetailScreenTypeA() }
        composable(Screen.ActivityDetailScreenTypeB.route) { WorkoutDetailScreenTypeB() }

    }


}