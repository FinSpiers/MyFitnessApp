@file:OptIn(ExperimentalMaterial3Api::class)

package uniks.cc.myfitnessapp.feature_dashboard.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import uniks.cc.myfitnessapp.core.presentation.components.WorkoutFab
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.presentation.components.*
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashBoardScreen(
    borderStroke: Dp = 2.dp,
) {
    val viewModel: DashBoardViewModel = hiltViewModel()
    val hasGpsPermission = ContextCompat
        .checkSelfPermission(LocalContext.current, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    MyFitnessAppTheme {
        Scaffold(
            floatingActionButton = { WorkoutFab(viewModel::onNavigationAction, viewModel::onWorkoutAction, viewModel.hasCurrentWorkout()) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            ) {
                var hasError = false
                var errorTitle = ""
                var errorText = ""
                val dashBoardState = viewModel.dashBoardState
                if (hasGpsPermission) {
                    viewModel.checkGpsState()
                    if (dashBoardState.value.hasGpsConnection) {
                        if (viewModel.hasInternetConnection()) {
                            viewModel.setWeatherData()
                            WeatherBox(
                                borderStroke = borderStroke,
                                currentTemp = dashBoardState.value.currentWeatherData.currentTemperature,
                                isWeatherGood = dashBoardState.value.currentWeatherData.isWeatherGood,
                                currentWeatherMain = dashBoardState.value.currentWeatherData.currentWeatherMain
                            )
                        } else {
                            errorTitle = stringResource(id = R.string.warning_no_internet_connection_title)
                            errorText = stringResource(id = R.string.warning_no_internet_connection_text)
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
                    if (errorTitle == stringResource(id = R.string.warning_no_gps_permission_title)) {
                        WarningBox(errorTitle, errorText) {
                            viewModel.onNavigationAction(NavigationEvent.OnOpenAppSettingsClick)
                        }
                    } else {
                        WarningBox(errorTitle, errorText)
                    }
                }

                Column(modifier = Modifier.fillMaxSize()) {
                    CurrentStepsBox(steps = dashBoardState.value.steps)
                    RecentWorkouts(
                        viewModel.getAllWorkouts(),
                        viewModel::onWorkoutDetailClick,
                        viewModel.getCurrentWorkout()
                    )

                }
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