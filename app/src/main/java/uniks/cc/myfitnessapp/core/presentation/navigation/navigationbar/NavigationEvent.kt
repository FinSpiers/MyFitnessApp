package uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar

sealed class NavigationEvent {
    object OnDashBoardPressed : NavigationEvent()
    object OnSettingsPressed : NavigationEvent()
}
