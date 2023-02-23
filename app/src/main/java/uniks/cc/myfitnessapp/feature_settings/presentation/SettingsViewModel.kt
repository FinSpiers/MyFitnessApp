package uniks.cc.myfitnessapp.feature_settings.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uniks.cc.myfitnessapp.feature_settings.domain.model.Settings
import uniks.cc.myfitnessapp.feature_settings.domain.repository.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    // Create private and public version of settings state
    private val _settingsState = mutableStateOf(SettingsState())
    val settingsState: MutableState<SettingsState> = _settingsState

    init {
        viewModelScope.launch {
            // Load settings from the database
            val loadedSettings = settingsRepository.getSettingsFromDatabase()

            // If not null update the state
            if (loadedSettings != null) {
                _settingsState.value = _settingsState.value.copy(
                    isMale = loadedSettings.isMale,
                    weight = loadedSettings.weight,
                    height = loadedSettings.height,
                    birthDateAsTimeStamp = loadedSettings.birthDateAsTimeStamp
                )
            } else {
                _settingsState.value = _settingsState.value.copy(settings = Settings())
            }
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

}