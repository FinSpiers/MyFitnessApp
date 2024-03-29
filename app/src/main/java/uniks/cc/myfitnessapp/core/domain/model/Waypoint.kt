package uniks.cc.myfitnessapp.core.domain.model

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "TraveledRoute"
)
data class Waypoint(
    var workoutId: Int,
    var timeStamp: Long,
    var locationLat: Double,
    var locationLon: Double,
    var currentPace : Double,
    var altitude : Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun calculateDistanceTo(waypoint: Waypoint) : Int  {
        val startPoint = Location("StartPoint").apply {
            latitude = this@Waypoint.locationLat
            longitude = this@Waypoint.locationLon
        }
        val endPoint = Location("EndPoint").apply {
            latitude = waypoint.locationLat
            longitude = waypoint.locationLon
        }
        return startPoint.distanceTo(endPoint).toInt()
    }
}