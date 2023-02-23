package uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar

import uniks.cc.myfitnessapp.core.domain.util.Screen

data class NavigationBarState(
    val currentRoute: String = Screen.DashBoardScreen.route,
    val subRoute : String? = null
)
