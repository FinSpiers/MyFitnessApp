package uniks.cc.myfitnessapp.core.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import uniks.cc.myfitnessapp.feature_dashboard.data.network.response.toCurrentWeatherData
import uniks.cc.myfitnessapp.feature_dashboard.data.network.OpenWeatherApiService
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.util.hasActivityRecognitionPermission
import uniks.cc.myfitnessapp.core.domain.util.hasLocationPermission
import uniks.cc.myfitnessapp.core.domain.util.hasNotificationPermission
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarState
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.domain.model.CurrentWeatherData
import javax.inject.Inject
import kotlin.reflect.KFunction1

class CoreRepositoryImpl @Inject constructor(override var context: Context) : CoreRepository {
    override lateinit var onNavigationAction: KFunction1<NavigationEvent, Unit>
    override var navBarState: NavigationBarState? = null
    override var isLocationPermissionGranted: Boolean = false
    override var isActivityRecognitionPermissionGranted: Boolean = false
    override var isNotificationPermissionGranted: Boolean = false

    init {
        checkLocationPermission()
        checkActivityRecognitionPermission()
        checkNotificationPermission()
    }

    override fun checkActivityRecognitionPermission() {
       isActivityRecognitionPermissionGranted = context.hasActivityRecognitionPermission()
    }

    override fun checkLocationPermission() {
        isLocationPermissionGranted = context.hasLocationPermission()
    }

    override fun checkNotificationPermission() {
        isNotificationPermissionGranted = context.hasNotificationPermission()
    }


}
