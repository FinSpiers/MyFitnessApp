package uniks.cc.myfitnessapp.feature_workout.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.R
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun WorkoutDetailScreenTypeB(
    imageId: Int,
    workoutName: String,
    date: String,
    durationValue: Double,
    durationUnit: String,
    repetitionsValue: Int,
    kcalValue: Int,
    kcalUnit: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(10.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(id = imageId),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
                contentDescription = workoutName,
                modifier = Modifier
                    .size(35.dp)
                    .padding(3.dp)
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
        Spacer(modifier = Modifier.height(20.dp))
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
                text = "$durationValue $durationUnit",
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
                text = "Repetitions",
                textAlign = TextAlign.Left
            )
            Text(
                text = repetitionsValue.toString(),
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
                text = "$kcalValue $kcalUnit",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Right
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutDetailScreenTypeBPreview() {
    MyFitnessAppTheme {
        WorkoutDetailScreenTypeB(
            R.drawable.image_push_up,
            "PushUps",
            "20.02.2023, 10:30",
            23.0,
            "min",
            14,
            2345,
            "kcal",
        )
    }
}