package uniks.cc.myfitnessapp

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import org.junit.Test

import org.junit.Assert.*
import uniks.cc.myfitnessapp.core.domain.model.Waypoint
import uniks.cc.myfitnessapp.feature_current_workout.data.calculator.DistanceCalculator
import uniks.cc.myfitnessapp.feature_current_workout.domain.util.*
import uniks.cc.myfitnessapp.feature_current_workout.domain.util.stopwatch.*
import java.time.LocalDateTime

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}