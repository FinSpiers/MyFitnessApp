package uniks.cc.myfitnessapp.core.domain.util

private const val DASHBOARD_ROUTE = "dashboard"
sealed class Screen(val route: String) {

    object OnBoardingScreen : Screen("on_boarding")
    object DashBoardScreen : Screen(DASHBOARD_ROUTE)
    object SettingsScreen : Screen("settings")
    object CurrentWorkoutScreen : Screen("$DASHBOARD_ROUTE/current_activity")
    object ActivityDetailScreen : Screen("$DASHBOARD_ROUTE/activity_detail")



}
