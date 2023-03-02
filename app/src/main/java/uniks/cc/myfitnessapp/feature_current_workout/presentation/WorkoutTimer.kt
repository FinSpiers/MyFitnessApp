package uniks.cc.myfitnessapp.feature_current_workout.presentation

import android.os.SystemClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import uniks.cc.myfitnessapp.feature_core.presentation.components.DataBox

@Composable
fun WorkoutTimer() {
    val initialMillis: Long = 0
    val step: Long = 200

    val currentTime = remember { mutableStateOf(initialMillis) }
    val currSec = remember { mutableStateOf(0L) }
    val currMin = remember { mutableStateOf(0L) }

    LaunchedEffect(initialMillis, step) {
        val startTime = SystemClock.uptimeMillis()
        while (isActive) {
            currentTime.value = (SystemClock.uptimeMillis() - startTime).coerceAtLeast(0)
            currSec.value = currentTime.value / 1000 % 60
            currMin.value = currentTime.value / 1000 / 60
            delay(step)
        }
    }

    DataBox(
        title = "Duration",
        data = "${currMin.value}:${
            if ((currSec.value / 10) < 1) {
                "0" + currSec.value
            } else {
                currSec.value
            }
        }",
        unit = "min"
    )

}