package uniks.cc.myfitnessapp.feature_dashboard.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.feature_dashboard.presentation.components.ActivityPlanner
import uniks.cc.myfitnessapp.feature_dashboard.presentation.components.CurrentWeatherBox
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun DashBoardScreen() {
    val currentTemp = 10
    val imageVector = Icons.Default.WbSunny
    val isWeatherGood = true
    val borderStroke = 2.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(borderStroke, MaterialTheme.colorScheme.secondary)

        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .border(borderStroke, MaterialTheme.colorScheme.secondary
                        )
            ) {
                CurrentWeatherBox(temperature = currentTemp, imageVector = imageVector)

            }
            ActivityPlanner(isWeatherGood = isWeatherGood)
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