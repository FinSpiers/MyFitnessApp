package uniks.cc.myfitnessapp.core.domain.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.hasActivityRecognitionPermission() : Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACTIVITY_RECOGNITION
    ) == PackageManager.PERMISSION_GRANTED
}