package uniks.cc.myfitnessapp.feature_settings.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uniks.cc.myfitnessapp.core.domain.model.Workout
import uniks.cc.myfitnessapp.feature_current_workout.data.data_source.StepCounterResetWorker
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import uniks.cc.myfitnessapp.feature_settings.domain.model.Settings
import uniks.cc.myfitnessapp.feature_settings.domain.repository.SettingsRepository
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val workoutRepository: WorkoutRepository,
    private val workManager: WorkManager
) : ViewModel() {
    // Create private and public version of settings state
    private val _settingsState = mutableStateOf(SettingsState())
    val settingsState: MutableState<SettingsState> = _settingsState

    init {
        viewModelScope.launch {
            // Load settings from the database
            val loadedSettings = settingsRepository.getSettingsFromDatabase()

            // If not null update the state
            _settingsState.value = _settingsState.value.copy(
                isMale = loadedSettings.isMale,
                weight = loadedSettings.weight,
                height = loadedSettings.height,
                birthDateAsTimeStamp = loadedSettings.birthDateAsTimeStamp
            )
        }
    }

    fun setBodyInfo(_weight: Int, _height: Int, _isMale: Boolean, _birthDateAsTimeStamp: Long) {
        _settingsState.value =
            _settingsState.value.copy(
                weight = _weight,
                height = _height,
                isMale = _isMale,
                birthDateAsTimeStamp = _birthDateAsTimeStamp,
                settings = _settingsState.value.settings.apply {
                    weight = _weight
                    height = _height
                    isMale = _isMale
                    birthDateAsTimeStamp = _birthDateAsTimeStamp
                }
            )
        viewModelScope.launch {
            settingsRepository.saveSettingsToDatabase(_settingsState.value.settings)
        }
    }

    fun resetAllData() {
        _settingsState.value =
            _settingsState.value.copy(
                weight = 0,
                height = 0,
                isMale = true,
                birthDateAsTimeStamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).epochSecond,
                settings = _settingsState.value.settings.apply {
                    weight = 0
                    height = 0
                    isMale = true
                    birthDateAsTimeStamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).epochSecond
                }
            )
        setBodyInfo(
            _weight = _settingsState.value.weight,
            _height = _settingsState.value.height,
            _isMale = _settingsState.value.isMale,
            _birthDateAsTimeStamp = _settingsState.value.birthDateAsTimeStamp
        )
        viewModelScope.launch {
            for (workout : Workout in workoutRepository.getAllWorkoutsFromDatabase()) {
                workoutRepository.deleteWorkoutFromDatabase(workout)
            }
            if (workoutRepository.currentWorkout != null) {
                workoutRepository.currentWorkout = null
            }
            val resetRequest : OneTimeWorkRequest = OneTimeWorkRequestBuilder<StepCounterResetWorker>().build()
            workManager.enqueue(resetRequest)
        }
    }

}