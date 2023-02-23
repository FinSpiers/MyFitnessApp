package uniks.cc.myfitnessapp.feature_dashboard.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uniks.cc.myfitnessapp.core.domain.model.sport_activities.SportActivity
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val coreRepository: CoreRepository
) : ViewModel() {

    init {

    }

    fun onSportActivityDetailClick() {
        val x = SportActivity.WalkingHiking(1.5, 5.3, 6.0, 4.5, 357)
        coreRepository.navigate(NavigationEvent.OnWorkoutDetailClick(x))
    }

}