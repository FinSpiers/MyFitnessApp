package uniks.cc.myfitnessapp.feature_active_workout.domain.util

import uniks.cc.myfitnessapp.R

object WorkoutMap {
    val map : LinkedHashMap<String, Int> = linkedMapOf()

    init {
        map["Walking"] = R.drawable.image_walking
        map["Running"] = R.drawable.image_jogging
        map["Bicycling"] = R.drawable.image_bicycling
        map["PushUps"] = R.drawable.image_push_ups
        map["SitUps"] = R.drawable.image_sit_ups
        map["Squats"] = R.drawable.image_squats
    }
}