package uniks.cc.myfitnessapp.feature_workout.data.current_workout.calculator

import uniks.cc.myfitnessapp.core.domain.util.Constants

object EnergyCalculator {
    fun calculateBurnedEnergy(workoutName : String, durationSeconds : Int, weight : Int = 70, repetitions : Int = 1) : Int {
        var factor : Double
        when(workoutName) {
            Constants.WORKOUT_WALKING -> {
                factor = when(weight) {
                    in 0..62 -> 5.5
                    in 63..67 -> 6.3
                    in 68..72 -> 7.0
                    in 73..77 -> 7.5
                    in 78..82 -> 8.0
                    in 83..87 -> 8.6
                    in 88..92 -> 9.5
                    in 93..97 -> 10.0
                    in 98..102 -> 10.6
                    else -> 11.3
                }
            }
            Constants.WORKOUT_RUNNING -> {
                factor = when(weight) {
                    in 0..62 -> 13.0
                    in 63..67 -> 14.5
                    in 68..72 -> 16.0
                    in 73..77 -> 17.5
                    in 78..82 -> 19.0
                    in 83..87 -> 20.0
                    in 88..92 -> 22.0
                    in 93..97 -> 23.5
                    in 98..102 -> 24.5
                    else -> 26.5
                }
            }
            Constants.WORKOUT_BICYCLING -> {
                factor = when(weight) {
                    in 0..62 -> 6.5
                    in 63..67 -> 7.5
                    in 68..72 -> 8.0
                    in 73..77 -> 9.0
                    in 78..82 -> 9.5
                    in 83..87 -> 10.3
                    in 88..92 -> 11.0
                    in 93..97 -> 11.7
                    in 98..102 -> 12.5
                    else -> 13.6
                }
            }
            else -> {
                factor = repetitions / 2 * when(weight) {
                    in 0..62 -> 11.0
                    in 63..67 -> 13.0
                    in 68..72 -> 14.3
                    in 73..77 -> 15.0
                    in 78..82 -> 16.5
                    in 83..87 -> 17.5
                    in 88..92 -> 19.0
                    in 93..97 -> 20.5
                    in 98..102 -> 21.8
                    else -> 23.5
                }
            }
        }
        return (factor * (durationSeconds.toDouble() / 60)).toInt()
    }
}