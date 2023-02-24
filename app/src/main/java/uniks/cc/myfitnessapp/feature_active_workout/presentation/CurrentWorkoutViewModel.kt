package uniks.cc.myfitnessapp.feature_active_workout.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CurrentWorkoutViewModel @Inject constructor() : ViewModel()  {
    var currentWorkout : MutableState<CurrentWorkoutState> = mutableStateOf(CurrentWorkoutState())


    /*
    imageId: Int = R.drawable.image_walking,
    workoutName: String = "Walking",
    durationValue: Double = 23.4,
    distanceValue: Double = 2.1,
    paceValue: Double = 4.3,
     */






}