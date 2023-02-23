package uniks.cc.myfitnessapp.feature_settings.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_SETTINGS_ID = 0

@Entity(tableName = "settings")
class Settings(
    var isMale: Boolean = true,
    var height: Int = 0,
    var weight: Int = 0,
    var birthDateAsTimeStamp: Long = 0
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_SETTINGS_ID
}