package uniks.cc.myfitnessapp.feature_workout.domain.current_workout.location_client

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import uniks.cc.myfitnessapp.core.domain.util.hasLocationPermission

class LocationClientImpl(
    private val context : Context,
    private val client : FusedLocationProviderClient
) : LocationClient {

    lateinit var locationCallback : LocationCallback
    @SuppressLint("MissingPermission")
    override fun getLocationUpdates(interval: Long): Flow<Location> {
        return callbackFlow {
            if (!context.hasLocationPermission()) {
                throw LocationClient.LocationException("Missing location permission")
            }
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            if (!isGpsEnabled && !isNetworkEnabled) {
                throw LocationClient.LocationException("GPS is disabled")
            }

            val request = LocationRequest.Builder(interval)
                .setMinUpdateIntervalMillis(interval)
                .setMaxUpdateDelayMillis(interval)
                .build()

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.locations.lastOrNull()?.let { location: Location ->
                        launch { send(location) }
                    }
                }
            }
            client.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())

            awaitClose {
                stopLocationUpdates()
            }
        }
    }

    override fun stopLocationUpdates() {
        client.removeLocationUpdates(locationCallback)
    }
}