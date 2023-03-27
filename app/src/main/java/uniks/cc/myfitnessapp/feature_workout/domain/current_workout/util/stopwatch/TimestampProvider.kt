package uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch

interface TimestampProvider {

    fun getMilliseconds(): Long
}