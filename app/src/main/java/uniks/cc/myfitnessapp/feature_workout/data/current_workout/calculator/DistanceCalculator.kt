package uniks.cc.myfitnessapp.feature_workout.data.current_workout.calculator

import android.location.Location
import uniks.cc.myfitnessapp.core.domain.model.Waypoint

object DistanceCalculator {
    fun calculateMeters(waypoints : List<Waypoint>) : Int {
        var distance : Float = 0f
        for (i in 0..waypoints.size - 2) {
            if (waypoints[i].workoutId == waypoints[i + 1].workoutId) {
                val startPoint = Location("StartPoint").apply {
                    latitude = waypoints[i].locationLat
                    longitude = waypoints[i].locationLon
                }
                val endPoint = Location("EndPoint").apply {
                    latitude = waypoints[i+1].locationLat
                    longitude = waypoints[i+1].locationLon
                }
                distance += startPoint.distanceTo(endPoint)
            }
        }
        return distance.toInt()
    }

    fun calculateKilometers(waypoints : List<Waypoint>) : Double {
        var distance : Float = 0f
        for (i in 0..waypoints.size - 2) {
            if (waypoints[i].workoutId == waypoints[i + 1].workoutId) {
                val startPoint = Location("StartPoint").apply {
                    latitude = waypoints[i].locationLat
                    longitude = waypoints[i].locationLon
                }
                val endPoint = Location("EndPoint").apply {
                    latitude = waypoints[i+1].locationLat
                    longitude = waypoints[i+1].locationLon
                }
                distance += startPoint.distanceTo(endPoint)
            }
        }
        return distance.toDouble()
    }
}