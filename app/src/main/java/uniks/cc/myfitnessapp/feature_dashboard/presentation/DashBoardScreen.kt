@file:OptIn(ExperimentalMaterial3Api::class)

package uniks.cc.myfitnessapp.feature_dashboard.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import uniks.cc.myfitnessapp.R
import uniks.cc.myfitnessapp.core.presentation.components.WorkoutFab
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import uniks.cc.myfitnessapp.feature_dashboard.presentation.components.*
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashBoardScreen(
    hasGpsPermission: Boolean = ContextCompat
        .checkSelfPermission(LocalContext.current, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
) {
    val viewModel: DashBoardViewModel = hiltViewModel()
    MyFitnessAppTheme {
        Scaffold(
            floatingActionButton = {
                WorkoutFab(
                    viewModel::onNavigationAction,
                    viewModel::onWorkoutAction,
                    viewModel.hasCurrentWorkout()
                )
            },
        ) {
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
                            WeatherBox(
                                currentTemp = viewModel.dashBoardState.value.currentWeatherData.currentTemperature,
                                isWeatherGood = viewModel.dashBoardState.value.currentWeatherData.isWeatherGood,
                                currentWeatherMain = viewModel.dashBoardState.value.currentWeatherData.currentWeatherMain
                            )
                            viewModel.setWeatherData()
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
                    if (errorTitle == stringResource(id = R.string.warning_no_gps_permission_title)) {
                        WarningBox(errorTitle, errorText) {
                            viewModel.onNavigationAction(NavigationEvent.OnOpenAppSettingsClick)
                        }
                    } else {
                        WarningBox(errorTitle, errorText)
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 4.dp)
                ) {
                    CurrentStepsBox(
                        steps = viewModel.stepCounterStateFlow.collectAsState().value, //- viewModel.getOldStepCount(),
                        dialogState = viewModel.dialogStateFlow
                    )
                    StepsHistory(viewModel.dialogStateFlow)
                    Spacer(modifier = Modifier.height(4.dp))
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