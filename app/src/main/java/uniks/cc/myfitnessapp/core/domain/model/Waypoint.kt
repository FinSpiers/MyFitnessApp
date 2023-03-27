package uniks.cc.myfitnessapp.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "TraveledRoute"
    /*,
    foreignKeys = [ForeignKey(
        entity = Workout::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("workoutId"),
        onDelete = ForeignKey.CASCADE
    )]

     */
)
data class Waypoint(
    var workoutId: Int,
    var timeStamp: Long,
    var locationLat: Double,
    var locationLon: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}