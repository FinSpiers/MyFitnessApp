package uniks.cc.myfitnessapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uniks.cc.myfitnessapp.feature_dashboard.presentation.DashBoardScreen
import uniks.cc.myfitnessapp.feature_settings.presentation.SettingsScreen

@Composable
fun NavigationHost(navController : NavHostController, startDestination : String, bottomNavigationDestinations : List<String>) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(bottomNavigationDestinations[0]) { DashBoardScreen() }
        composable(bottomNavigationDestinations[1]) { SettingsScreen() }
    }


}