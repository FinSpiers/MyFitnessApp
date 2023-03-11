package uniks.cc.myfitnessapp.feature_workout_detail.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import uniks.cc.myfitnessapp.core.domain.model.Waypoint

@Composable
fun MapBox(hasInternetConnection: Boolean = false, path: List<Waypoint> = emptyList()) {
    if (hasInternetConnection && path.isNotEmpty()) {
        val cameraPositionState = rememberCameraPositionState()
        val cameraPosition = CameraPosition.builder().apply {
            target(LatLng(path[0].locationLat, path[0].locationLon))
            zoom(15f)
        }.build()
        cameraPositionState.position = cameraPosition
        Box(
            contentAlignment = Alignment.Center, modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .aspectRatio(1f)
                .shadow(20.dp)
                .clip(MaterialTheme.shapes.large)
        ) {
            GoogleMap(cameraPositionState = cameraPositionState, modifier = Modifier.fillMaxSize())
        }
    }
}
