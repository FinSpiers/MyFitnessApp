package uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import uniks.cc.myfitnessapp.core.domain.model.BottomNavItem
import kotlin.reflect.KFunction1

@Composable
fun BottomNavigationBar(
    navBarState : NavigationBarState,
    onEvent: KFunction1<NavigationBarEvent, Unit>
) {
    val dashboard = "dashboard"
    val settings = "settings"
    val items = listOf(
        BottomNavItem("Dashboard", dashboard, 0, navBarState.currentRoute == dashboard),
        BottomNavItem("Settings", settings, 0, navBarState.currentRoute == settings)
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = item.IconId), contentDescription = item.title)},
                label = { Text(text = item.title) },
                selected = item.isSelected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    unselectedTextColor = MaterialTheme.colorScheme.secondary
                ),
                onClick = {
                          when(item.route) {
                              dashboard -> onEvent(NavigationBarEvent.OnDashBoardPressed)
                              settings -> onEvent(NavigationBarEvent.OnSettingsPressed)
                          }
                },
            )
        }
    }
}