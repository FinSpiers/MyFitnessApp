package uniks.cc.myfitnessapp.feature_current_workout.domain.util.stopwatch

sealed class StopwatchState {
    data class Paused(
        val elapsedTime: Long
    ) : StopwatchState()

    data class Running(
        val startTime: Long,
        val elapsedTime: Long
    ) : StopwatchState()
}
