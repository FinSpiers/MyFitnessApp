package uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import uniks.cc.myfitnessapp.core.domain.model.bottom_navigation.NavItem
import kotlin.reflect.KFunction1

@Composable
fun BottomNavigationBar(
    navBarState: NavigationBarState,
    onEvent: KFunction1<NavigationEvent, Unit>
) {

    val dashboard = "dashboard"
    val settings = "settings"

    val items = listOf(
        NavItem("Dashboard", dashboard, Icons.Default.Dashboard, true),
        NavItem("Settings", settings, Icons.Default.Settings, true),
    )

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        items.forEach { item ->
            if (item.isBottomNavItem) {
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
                            dashboard -> onEvent(NavigationEvent.OnDashBoardPressed)
                            settings -> onEvent(NavigationEvent.OnSettingsPressed)
                        }
                    },
                )
            }
        }
    }
}