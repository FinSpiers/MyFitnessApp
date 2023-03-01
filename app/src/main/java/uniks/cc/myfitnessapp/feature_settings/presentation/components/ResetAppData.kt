package uniks.cc.myfitnessapp.feature_settings.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetAppData(
    resetAllData: () -> Unit
) {

    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        AlertDialog(onDismissRequest = {
            openDialog.value = false
        }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .padding(10.dp),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Do you want to delete ALL data ?", fontSize = 25.sp, modifier = Modifier.padding(10.dp), textAlign = TextAlign.Center)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            resetAllData()
                            openDialog.value = false
                        }) {
                            Text(text = "YES", fontSize = 19.sp)
                        }
                        Button(onClick = { openDialog.value = false }) {
                            Text(text = "NO", fontSize = 19.sp)
                        }
                    }
                }
            }
        }
    }

    Button(onClick = {
        openDialog.value = true

    }, modifier = Modifier.padding(top = 20.dp)) {
        Text(text = "Reset App Data")
    }
}
