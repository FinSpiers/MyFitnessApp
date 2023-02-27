package uniks.cc.myfitnessapp.feature_dashboard.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import uniks.cc.myfitnessapp.R
import uniks.cc.myfitnessapp.feature_dashboard.presentation.components.*
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashBoardScreen(
    borderStroke: Dp = 2.dp,
) {
    val hasGpsPermission = ContextCompat.checkSelfPermission(
        LocalContext.current,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val viewModel: DashBoardViewModel = hiltViewModel()

    MyFitnessAppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {
            var hasError = false
            var errorTitle = ""
            var errorText = ""
            if (hasGpsPermission) {
                if (viewModel.hasGpsSignal()) {
                    if (viewModel.hasInternetConnection()) {
                        viewModel.setWeatherData()
                        WeatherBox(
                            borderStroke = borderStroke,
                            currentTemp = viewModel.dashBoardState.value.currentWeatherData.currentTemperature,
                            isWeatherGood = viewModel.dashBoardState.value.currentWeatherData.isWeatherGood,
                            currentWeatherMain = viewModel.dashBoardState.value.currentWeatherData.currentWeatherMain
                        )
                    } else {
                        errorTitle =
                            stringResource(id = R.string.warning_no_internet_connection_title)
                        errorText =
                            stringResource(id = R.string.warning_no_internet_connection_text)
                        hasError = true
                    }
                } else {
                    errorTitle = stringResource(id = R.string.warning_no_gps_signal_title)
                    errorText = stringResource(id = R.string.warning_no_gps_signal_text)
                    hasError = true
                }
            } else {
                errorTitle = stringResource(id = R.string.warning_no_gps_permission_title)
                errorText = stringResource(id = R.string.warning_no_gps_permission_text)
                hasError = true
            }
            if (hasError) {
                WarningBox(errorTitle, errorText)
            }

            Column(modifier = Modifier.fillMaxSize()) {
                CurrentStepsBox(steps = viewModel.dashBoardState.value.steps)
                RecentWorkouts(
                    viewModel.workouts,
                    viewModel::onSportActivityDetailClick,
                    viewModel.dashBoardState.value.currentWorkout
                )

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