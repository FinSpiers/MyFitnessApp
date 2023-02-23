package uniks.cc.myfitnessapp.feature_settings.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun GenderPicker(
    isMale: MutableState<Boolean>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center

    ) {

        Text(text = "Male")
        RadioButton(selected = isMale.value, onClick = {
            isMale.value = true
        })
        Text(text = "Female")
        RadioButton(selected = !isMale.value, onClick = {
            isMale.value = false
        })
    }
}