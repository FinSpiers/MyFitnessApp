package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarState

@Composable
fun StartActivity(navBarState : NavigationBarState) {
    if (navBarState.currentRoute == "dashboard") {
        ExtendedFloatingActionButton(
            text = { Text(text = "Start Workout") },
            icon = { Icon(Icons.Filled.Add, "") },
            onClick = { /*TODO*/ })
    }
}