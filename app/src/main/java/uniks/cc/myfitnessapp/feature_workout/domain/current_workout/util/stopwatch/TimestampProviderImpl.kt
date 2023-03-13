package uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util.stopwatch

import java.time.Instant

class TimestampProviderImpl : TimestampProvider {
    override fun getMilliseconds(): Long {
        return Instant.now().toEpochMilli()
    }
}