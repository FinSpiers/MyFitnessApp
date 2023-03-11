package uniks.cc.myfitnessapp.feature_current_workout.domain.util.stopwatch

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class StopwatchOrchestrator(
    private val stopwatchStateHolder: StopwatchStateHolder,
    private val scope : CoroutineScope
) {

    private var job : Job? = null
    private val mutableTicker = MutableStateFlow("")
    val ticker : StateFlow<String> = mutableTicker

    fun start() {
        if (job == null) {
            startJob()
            stopwatchStateHolder.start()
        }
    }

    fun pause() {
        stopwatchStateHolder.pause()
        stopJob()
    }

    fun stop() {
        stopwatchStateHolder.stop()
        stopJob()
        clearValue()
    }

    private fun startJob() {
        scope.launch {
            while (isActive) {
                mutableTicker.value = stopwatchStateHolder.getStringRepresentation()
                delay(20)
            }
        }
    }

    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValue() {
        mutableTicker.value = ""
    }
}