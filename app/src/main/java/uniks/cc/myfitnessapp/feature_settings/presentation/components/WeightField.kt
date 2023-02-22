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
fun WeightField() {

    val weight = remember { mutableStateOf("") }

    val isErrorWeight = remember { mutableStateOf(false) }

    val maxChar = 4

    fun validateWeight(value: Int) {
        isErrorWeight.value = value !in 0..400
        println(isErrorWeight.value)
    }

    fun isNumeric(toCheck: String) : Boolean {
        return toCheck.toIntOrNull() != null
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clip(MaterialTheme.shapes.medium),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.End),
        value = weight.value,
        onValueChange = {
            if (it.length <= maxChar && isNumeric(it) && it != "") {
                weight.value = it
                validateWeight(it.toInt())
            }
            if (it == "") weight.value = it
        },
        placeholder = {
            Text(
                text = "Weight",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        suffix = { Text(text = "  kg") },
        trailingIcon = {
            if (isErrorWeight.value)
                Icon(Icons.Filled.Error, "error", tint = Color.Red)
        },
        singleLine = true,
        isError = isErrorWeight.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    if (isErrorWeight.value) {
        Text(
            text = "Not a real Weight",
            color = Color.Red,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}