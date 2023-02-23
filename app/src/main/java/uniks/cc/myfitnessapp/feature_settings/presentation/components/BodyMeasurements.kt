package uniks.cc.myfitnessapp.feature_settings.presentation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime


@Composable
fun BodyMeasurements(
    weight: MutableState<String>,
    height: MutableState<String>,
    isMale: MutableState<Boolean>,
    pickedDate: MutableState<LocalDateTime>,
    saveBodyInfo: () -> Unit
) {

    val focusManager = LocalFocusManager.current
    val interactionSource = MutableInteractionSource()


    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.medium
            )
            .fillMaxWidth(0.9f)
            .fillMaxHeight(0.7f)
            .padding(top = 15.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(interactionSource = interactionSource, indication = null) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        HeightField(height)

        WeightField(weight)

        Spacer(modifier = Modifier.height(15.dp))

        Column(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.medium
                )
                .fillMaxWidth(.75f)
                .fillMaxHeight(.8f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            GenderPicker(isMale)

            BirthdatePicker(pickedDate)
        }
        val context: Context = LocalContext.current
        Button(
            onClick = {

                if (weight.value.toInt() in 1..400 && height.value.toInt() in 1..300) {
                    saveBodyInfo()
                    Toast.makeText(
                        context,
                        "Saved Data",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Cant Save Wrong Data",
                        Toast.LENGTH_LONG
                    ).show()
                }

            },
        ) {
            Text(text = "Save Settings")
        }
    }
}