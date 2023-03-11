package uniks.cc.myfitnessapp.feature_current_workout.domain.util.stopwatch

interface TimestampProvider {

    fun getMilliseconds() : Long
}