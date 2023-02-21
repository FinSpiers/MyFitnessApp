package uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import uniks.cc.myfitnessapp.core.domain.model.BottomNavItem
import kotlin.reflect.KFunction1

@Composable
fun BottomNavigationBar(
    navBarState: NavigationBarState,
    onEvent: KFunction1<NavigationBarEvent, Unit>
) {
    val dashboard = "dashboard"
    val settings = "settings"
    val items = listOf(
        BottomNavItem("Dashboard", dashboard, Icons.Default.Dashboard),
        BottomNavItem("Settings", settings, Icons.Default.Settings)
    )

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.Icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = item.route == navBarState.currentRoute,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary
                ),
                onClick = {
                    when (item.route) {
                        dashboard -> onEvent(NavigationBarEvent.OnDashBoardPressed)
                        settings -> onEvent(NavigationBarEvent.OnSettingsPressed)
                    }
                },
            )
        }
    }
}