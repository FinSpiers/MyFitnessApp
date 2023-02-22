@file:OptIn(ExperimentalMaterial3Api::class)

package uniks.cc.myfitnessapp.feature_dashboard.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    var hasPermission = false

    MyFitnessAppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {
            if (hasPermission) {
                WeatherBox(
                    borderStroke = borderStroke,
                    currentTemp = currentTemp,
                    imageVector = imageVector,
                    isWeatherGood = isWeatherGood
                )
            }
            else {
                NoPermissionBox(borderStroke)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                CurrentStepsBox(steps = 9213)
                RecentWorkouts()
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