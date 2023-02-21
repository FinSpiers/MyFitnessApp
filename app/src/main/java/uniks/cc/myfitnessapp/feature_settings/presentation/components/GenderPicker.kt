package uniks.cc.myfitnessapp.feature_settings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun GenderPicker() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

    ) {
        val isMaleState = remember { mutableStateOf(true) }


        Text(text = "Male")
        RadioButton(selected = isMaleState.value, onClick = {
            isMaleState.value = true
        })
        Text(text = "Female")
        RadioButton(selected = !isMaleState.value, onClick = {
            isMaleState.value = false
        })
    }
}