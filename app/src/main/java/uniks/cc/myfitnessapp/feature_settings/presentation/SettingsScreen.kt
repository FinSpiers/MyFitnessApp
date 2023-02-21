package uniks.cc.myfitnessapp.feature_settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsScreen() {
    Box(modifier = Modifier.background(color = Color.Black)){
        Text(text = "test")
    }

}

@Preview(showBackground = true)
@Composable
fun SetPreview() {
    SettingsScreen()
}