package uniks.cc.myfitnessapp.feature_current_workout.domain.util.stopwatch

import java.time.Instant

class TimestampProviderImpl : TimestampProvider {
    override fun getMilliseconds(): Long {
        return Instant.now().toEpochMilli()
    }
}