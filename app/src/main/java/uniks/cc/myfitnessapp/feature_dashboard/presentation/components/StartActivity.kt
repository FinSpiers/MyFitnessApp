package uniks.cc.myfitnessapp.feature_dashboard.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationEvent
import kotlin.reflect.KFunction1

const val WORKOUT_WALKING = "Walking"
const val WORKOUT_RUNNING = "Running"
const val WORKOUT_BICYCLING = "Bicycling"
const val WORKOUT_PUSHUPS = "PushUps"
const val WORKOUT_SITUPS = "SitUps"
const val WORKOUT_SQUATS = "Squats"
@Composable
fun StartActivity(
    navbarState: NavigationBarState,
    onEvent: KFunction1<NavigationEvent, Unit>,

    ) {
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
                    Column {
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
                            .padding(10.dp)
                            .clickable {
                                onEvent(NavigationEvent.OnStartWorkoutClick(WORKOUT_PUSHUPS))
                                openDialog.value = false
                            },
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
                            .padding(10.dp)
                            .clickable {
                                onEvent(NavigationEvent.OnStartWorkoutClick(WORKOUT_SITUPS))
                                openDialog.value = false
                            },
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
                            .padding(10.dp)
                            .clickable {
                                onEvent(NavigationEvent.OnStartWorkoutClick(WORKOUT_SQUATS))
                                openDialog.value = false
                            },
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
                            .padding(10.dp)
                            .clickable {
                                onEvent(NavigationEvent.OnStartWorkoutClick(WORKOUT_RUNNING))
                                openDialog.value = false
                            },
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
                            .padding(10.dp)
                            .clickable {
                                onEvent(NavigationEvent.OnStartWorkoutClick(WORKOUT_WALKING))
                                openDialog.value = false
                            },
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
                            .padding(10.dp)
                            .clickable {
                                onEvent(NavigationEvent.OnStartWorkoutClick(WORKOUT_BICYCLING))
                                openDialog.value = false
                            },
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
            icon = { Icon(Icons.Filled.Add, "Start workout") },
            onClick = {
                openDialog.value = !openDialog.value
            }
        )
    }
}