package uniks.cc.myfitnessapp.feature_dashboard.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.feature_dashboard.presentation.components.ActivityPlanner
import uniks.cc.myfitnessapp.feature_dashboard.presentation.components.CurrentStepsBox
import uniks.cc.myfitnessapp.feature_dashboard.presentation.components.CurrentWeatherBox
import uniks.cc.myfitnessapp.feature_dashboard.presentation.components.RecentWorkouts
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun DashBoardScreen(
    currentTemp: Int = 10,
    imageVector: ImageVector = Icons.Default.WbSunny,
    isWeatherGood: Boolean = false,
    borderStroke: Dp = 2.dp
) {
    MyFitnessAppTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        borderStroke,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.shapes.small
                    )
            ) {
                Column(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .border(
                            borderStroke,
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.shapes.small
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CurrentWeatherBox(temperature = currentTemp, imageVector = imageVector)
                }
                ActivityPlanner(isWeatherGood = isWeatherGood)
            }
            CurrentStepsBox(steps = 9213)
            RecentWorkouts()
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