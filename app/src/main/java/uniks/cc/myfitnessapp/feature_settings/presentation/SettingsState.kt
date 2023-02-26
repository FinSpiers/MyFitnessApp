package uniks.cc.myfitnessapp.feature_settings.presentation

import uniks.cc.myfitnessapp.feature_settings.domain.model.Settings
import java.time.LocalDateTime
import java.time.ZoneOffset

data class SettingsState(
    var isMale: Boolean = true,
    var weight: Int = 0,
    var height: Int = 0,
    var birthDateAsTimeStamp: Long = LocalDateTime.now().toInstant(ZoneOffset.UTC).epochSecond,
    var settings: Settings = Settings(
        isMale = isMale,
        weight = weight,
        height = height,
        birthDateAsTimeStamp = birthDateAsTimeStamp
    )
)