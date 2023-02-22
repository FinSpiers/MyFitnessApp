package uniks.cc.myfitnessapp.feature_settings.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeightField() {

    val height = remember { mutableStateOf("") }

    val isErrorHeight = remember { mutableStateOf(false) }
    val maxChar = 4

    fun validateHeight(value: Int) {
        isErrorHeight.value = value !in 0..300
    }

    fun isNumeric(toCheck: String): Boolean {
        return toCheck.toIntOrNull() != null
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(MaterialTheme.shapes.medium),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
        value = height.value,
        onValueChange = {
            if (it.length <= maxChar && isNumeric(it) && it != "") {
                height.value = it
                validateHeight(it.toInt())
            }
            if (it == "") height.value = it
        },
        placeholder = {
            Text(
                text = "Height",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        suffix = { Text(text = "  cm") },
        trailingIcon = {
            if (isErrorHeight.value)
                Icon(Icons.Filled.Error, "error", tint = Color.Red)
        },
        singleLine = true,
        isError = isErrorHeight.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    if (isErrorHeight.value) {
        Text(
            text = "Not a real Height",
            color = Color.Red,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}