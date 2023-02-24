package uniks.cc.myfitnessapp.feature_dashboard.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import uniks.cc.myfitnessapp.feature_dashboard.presentation.components.*
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashBoardScreen(
    currentTemp: Int = 10,
    imageVector: ImageVector = Icons.Default.WbSunny,
    isWeatherGood: Boolean = false,
    borderStroke: Dp = 2.dp
) {
    val hasGpsPermission = ContextCompat.checkSelfPermission(LocalContext.current, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    val viewModel : DashBoardViewModel = hiltViewModel()

    MyFitnessAppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {
            if (hasGpsPermission) {
                WeatherBox(
                    borderStroke = borderStroke,
                    currentTemp = currentTemp,
                    imageVector = imageVector,
                    isWeatherGood = isWeatherGood
                )
            } else {
                NoPermissionBox(borderStroke)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                CurrentStepsBox(steps = 9213)
                RecentWorkouts(viewModel.dashBoardState.value, viewModel::onSportActivityDetailClick)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashBoardPreview() {
    MyFitnessAppTheme {
        DashBoardScreen()
    }
}