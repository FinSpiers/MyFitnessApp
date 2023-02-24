package uniks.cc.myfitnessapp.feature_workout_detail.presentation

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
import androidx.hilt.navigation.compose.hiltViewModel
import uniks.cc.myfitnessapp.R
import uniks.cc.myfitnessapp.core.domain.util.TimestampConverter
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun WorkoutDetailScreen(
    durationUnit: String = "h",
    distanceUnit: String = "km",
    paceUnit: String = "km/h",
    kcalUnit: String = "kcal",
    viewModel: WorkoutDetailViewModel = hiltViewModel()
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
                painterResource(id = viewModel.model.imageId),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                contentDescription = viewModel.model.workoutName,
                modifier = Modifier
                    .size(35.dp)
                    .padding(3.dp)
            )
            Text(
                text = viewModel.model.workoutName,
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .padding(3.dp),
                textAlign = TextAlign.Center,
            )

        Text(
            text = TimestampConverter.convertToDate(viewModel.model.timeStamp),
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
            text = "${viewModel.model.duration} $durationUnit",
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
            text = "${viewModel.model.distance} $distanceUnit",
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
            text = "${viewModel.model.avgPace} $paceUnit",
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
            text = "${viewModel.model.kcal} $kcalUnit",
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
        WorkoutDetailScreen()
    }
}