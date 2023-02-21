package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import android.icu.number.NumberFormatter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.IncompleteCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme

@Composable
fun CurrentStepsBox(steps: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Daily steps", style = MaterialTheme.typography.titleMedium)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                imageVector = Icons.Default.IncompleteCircle,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
        Text(text = "$steps / 10000 steps", modifier = Modifier.padding(bottom = 8.dp), style = MaterialTheme.typography.titleLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun CurrentStepsBoxPreview() {
    MyFitnessAppTheme {
        CurrentStepsBox(steps = 10000)
    }
}