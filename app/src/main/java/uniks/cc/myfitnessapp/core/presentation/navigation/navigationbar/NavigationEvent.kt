package uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar

import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity

sealed class NavigationEvent {
    object OnDashBoardClicked : NavigationEvent()
    object OnSettingsClick : NavigationEvent()

    class OnStartWorkOut(val workoutId : Int) : NavigationEvent()

    class OnWorkoutDetailClick(val sportActivity: SportActivity) : NavigationEvent()
}
