package uniks.cc.myfitnessapp.feature_active_workout.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uniks.cc.myfitnessapp.R
import uniks.cc.myfitnessapp.feature_active_workout.domain.util.WorkoutMap
import uniks.cc.myfitnessapp.feature_core.presentation.components.DataBox
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun CurrentWorkoutScreen(viewModel: CurrentWorkoutViewModel = hiltViewModel()) {
    val currentWorkoutState = viewModel.currentWorkout.value

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
            val imageId = WorkoutMap.map[currentWorkoutState.workoutName]
            if(imageId != null) {
                Image(
                    painterResource(id = imageId),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface),
                    contentDescription = currentWorkoutState.workoutName,
                    modifier = Modifier.size(40.dp).clip(CircleShape).border(2.dp, MaterialTheme.colorScheme.outline, CircleShape),
                )
            }
            Text(
                text = currentWorkoutState.workoutName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(3.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DataBox(title = "Duration", data = (currentWorkoutState.durationValue).toString(), unit = "min")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DataBox(title = "Distance", data = (currentWorkoutState.distanceValue).toString(), unit = "km")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DataBox(title = "Pace", data = (currentWorkoutState.paceValue).toString(), unit = "km/h")
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WorkoutScreenTypeAPreview() {
    MyFitnessAppTheme {
        CurrentWorkoutScreen()
    }
}