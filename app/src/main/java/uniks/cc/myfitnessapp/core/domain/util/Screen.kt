package uniks.cc.myfitnessapp.core.domain.util

private const val DASHBOARD_ROUTE = "dashboard"
sealed class Screen(val route : String, val isBottomNavItem : Boolean) {
    object DashBoardScreen : Screen(DASHBOARD_ROUTE, true)
    object SettingsScreen : Screen( "settings", true)

    object ActivityScreenTypeA : Screen("$DASHBOARD_ROUTE/activity_type_A", false)
    object ActivityScreenTypeB : Screen( "$DASHBOARD_ROUTE/activity_type_B", false)
    object ActivityDetailScreenTypeA : Screen("$DASHBOARD_ROUTE/activity_detail_type_A", false)
    object ActivityDetailScreenTypeB : Screen("$DASHBOARD_ROUTE/activity_detail_type_B", false)

}
