package uniks.cc.myfitnessapp.feature_workout.domain.current_workout.util

import uniks.cc.myfitnessapp.R
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_BICYCLING
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_PUSH_UPS
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_RUNNING
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_SIT_UPS
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_SQUATS
import uniks.cc.myfitnessapp.core.domain.util.Constants.WORKOUT_WALKING

object WorkoutMap {
    val map: LinkedHashMap<String, Int> = linkedMapOf()

    init {
        map[WORKOUT_WALKING] = R.drawable.image_walking
        map[WORKOUT_RUNNING] = R.drawable.image_jogging
        map[WORKOUT_BICYCLING] = R.drawable.image_bicycling
        map[WORKOUT_PUSH_UPS] = R.drawable.image_push_ups
        map[WORKOUT_SIT_UPS] = R.drawable.image_sit_ups
        map[WORKOUT_SQUATS] = R.drawable.image_squats
    }
}