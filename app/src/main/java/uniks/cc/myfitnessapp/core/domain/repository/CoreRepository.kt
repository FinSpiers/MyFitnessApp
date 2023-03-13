package uniks.cc.myfitnessapp.core.domain.repository

import android.content.Context
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarState
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import kotlin.reflect.KFunction1

interface CoreRepository {
    var navBarState: NavigationBarState?
    var isLocationPermissionGranted : Boolean
    var isActivityRecognitionPermissionGranted : Boolean
    var onNavigationAction : KFunction1<NavigationEvent, Unit>
    var context : Context

    fun checkActivityRecognitionPermission()

    fun checkLocationPermission()
}