package uniks.cc.myfitnessapp.feature_current_workout.domain.util.stopwatch

class ElapsedTimeCalculator(private val timestampProvider: TimestampProvider) {

    fun calculate(state : StopwatchState.Running) : Long {
        val currentTimeStamp = timestampProvider.getMilliseconds()
        val timePassedSinceStart = if (currentTimeStamp > state.startTime) currentTimeStamp - state.startTime else 0
        return timePassedSinceStart + state.elapsedTime
    }
}
