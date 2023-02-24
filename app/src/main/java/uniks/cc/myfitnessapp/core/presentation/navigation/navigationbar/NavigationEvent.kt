package uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar

import uniks.cc.myfitnessapp.core.domain.model.Workout

sealed class NavigationEvent {
    object OnDashBoardClick : NavigationEvent()
    object OnSettingsClick : NavigationEvent()

    class OnStartWorkoutClick(val workoutName: String) : NavigationEvent()

    object onStopWorkoutClick : NavigationEvent()

    class OnWorkoutDetailClick(val workout: Workout) : NavigationEvent()


}
