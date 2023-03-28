package uniks.cc.myfitnessapp.feature_settings.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.system.exitProcess

@Composable
fun GoogleHealthConnect() {
    Button(onClick = { }, modifier = Modifier.padding(top = 20.dp)) {
        Text(text = "Google Health Connect")
    }
}