package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartActivity(navbarState: NavigationBarState) {

    val openDialog = remember { mutableStateOf(false) }

    if (openDialog.value) {
        Dialog(onDismissRequest = {
            openDialog.value = false
        }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.90f),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Column() {
                        Text(
                            text = "Chose your Workout",
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 30.sp,
                            modifier = Modifier
                                .padding(top = 4.dp, bottom = 4.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.background,
                                shape = MaterialTheme.shapes.medium
                            )
                            .fillMaxWidth()
                            .border(
                                width = 1.3.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Push-Ups", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    Row(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.background,
                                shape = MaterialTheme.shapes.medium
                            )
                            .fillMaxWidth()
                            .border(
                                width = 1.3.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Sit-Ups", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    Row(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.background,
                                shape = MaterialTheme.shapes.medium
                            )
                            .fillMaxWidth()
                            .border(
                                width = 1.3.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Squats", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    Row(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.background,
                                shape = MaterialTheme.shapes.medium
                            )
                            .fillMaxWidth()
                            .border(
                                width = 1.3.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Running", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    Row(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.background,
                                shape = MaterialTheme.shapes.medium
                            )
                            .fillMaxWidth()
                            .border(
                                width = 1.3.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Hiking", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                    Row(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.background,
                                shape = MaterialTheme.shapes.medium
                            )
                            .fillMaxWidth()
                            .border(
                                width = 1.3.dp,
                                color = MaterialTheme.colorScheme.secondary,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Cycling", fontSize = 25.sp, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }

    if (navbarState.currentRoute == "dashboard") {
        ExtendedFloatingActionButton(
            text = { Text(text = "Start Workout") },
            icon = { Icon(Icons.Filled.Add, "") },
            onClick = {
                openDialog.value = !openDialog.value
            }
        )
    }
}