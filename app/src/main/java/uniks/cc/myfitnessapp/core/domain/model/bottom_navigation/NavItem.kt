package uniks.cc.myfitnessapp.core.domain.model.bottom_navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val title : String,
    val route : String,
    val Icon : ImageVector,
    val isBottomNavItem: Boolean
)
