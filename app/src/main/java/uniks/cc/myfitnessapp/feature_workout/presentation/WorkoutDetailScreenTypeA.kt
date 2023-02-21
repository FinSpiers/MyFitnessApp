package uniks.cc.myfitnessapp.feature_workout.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NordicWalking
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun WorkoutDetailScreenTypeA(
    image : ImageVector,
    workoutName : String,
    date: String,
    durationValue: Double,
    durationUnit: String,
    distanceValue: Any,
    distanceUnit: String,
    avgPaceValue: Any,
    paceUnit: String,
    kcalValue: Any,
    kcalUnit: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RectangleShape
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = image,
                contentDescription = workoutName,
                modifier = Modifier.size(35.dp).padding(3.dp)
            )
            Text(
                text = workoutName,
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(3.dp),
                textAlign = TextAlign.Center,
            )
            Text(
                text = date,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                textAlign = TextAlign.Center,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
        ) {
            Text(
                text = "Duration",
                textAlign = TextAlign.Left
            )
            Text(
                text = "${durationValue.toString()} $durationUnit",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
        ) {
            Text(
                text = "Distance",
                textAlign = TextAlign.Left
            )
            Text(
                text = "${distanceValue.toString()} $distanceUnit",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
        ) {
            Text(
                text = "avg Pace",
                textAlign = TextAlign.Left
            )
            Text(
                text = "${avgPaceValue.toString()} $paceUnit",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
        ) {
            Text(
                text = "kcal",
                textAlign = TextAlign.Left
            )
            Text(
                text = "${kcalValue.toString()} $kcalUnit",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutDetailScreenTypeAPreview() {
    MyFitnessAppTheme {
        WorkoutDetailScreenTypeA(
            Icons.Default.NordicWalking,
            "Walking",
            "20.02.2023, 10:30",
            23.0,
            "min",
            43.2,
            "km",
            5.0,
            "km/h",
            2345.0,
            "kcal"
        )
    }
}