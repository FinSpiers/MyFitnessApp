package uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar

sealed class NavigationBarEvent {
    object OnDashBoardPressed : NavigationBarEvent()
    object OnSettingsPressed : NavigationBarEvent()
}
