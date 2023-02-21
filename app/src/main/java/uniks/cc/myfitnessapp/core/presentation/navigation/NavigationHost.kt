package uniks.cc.myfitnessapp.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uniks.cc.myfitnessapp.feature_dashboard.presentation.DashBoardScreen

@Composable
fun NavigationHost(bottomNavigationDestinations : List<String>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = bottomNavigationDestinations[0]) {
        composable(bottomNavigationDestinations[0]) { DashBoardScreen() }
    }


}