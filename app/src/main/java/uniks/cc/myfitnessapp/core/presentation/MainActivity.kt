package uniks.cc.myfitnessapp.core.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.Permission
import androidx.health.connect.client.records.ExerciseSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.presentation.navigation.NavigationHost
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.BottomNavigationBar
import uniks.cc.myfitnessapp.feature_workout.domain.repository.WorkoutRepository
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {
    @Inject
    lateinit var workoutRepository: WorkoutRepository

    @Inject
    lateinit var coreRepository: CoreRepository

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (HealthConnectClient.isAvailable(this)) {
            // Health Connect is available
            checkPermissionsAndRun()
        } else {
            Toast.makeText(
                this, "Health Connect is not available", Toast.LENGTH_SHORT
            ).show()
        }
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val navController = rememberNavController()
            viewModel.setNavController(navController)
            viewModel.setRepoContext(this)

            val navBarState = viewModel.navBarState.value

            MyFitnessAppTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            navBarState = navBarState,
                            onEvent = viewModel::onNavigationEvent
                        )
                    },
                ) {
                    NavigationHost(
                        navController = navController,
                        startDestination = navBarState.currentRoute
                    )
                }
            }
        }
    }
    private fun checkPermissionsAndRun() {

        val client = HealthConnectClient.getOrCreate(this)


        val permissionsSet = setOf(
            Permission.createWritePermission(StepsRecord::class),
            Permission.createReadPermission(StepsRecord::class),
            Permission.createWritePermission(ExerciseSessionRecord::class),
            Permission.createReadPermission(ExerciseSessionRecord::class)
        )


        // Create the permissions launcher.
        val requestPermissionActivityContract = client
            .permissionController
            .createRequestPermissionActivityContract()

        val requestPermissions = registerForActivityResult(
            requestPermissionActivityContract
        ) { granted ->
            if (granted.containsAll(permissionsSet)) {
                // Permissions successfully granted
                lifecycleScope.launch {
                    onPermissionAvailable(client)
                }
            } else {
                Toast.makeText(
                    this, "Permissions not granted", Toast.LENGTH_SHORT
                ).show()
            }
        }


        lifecycleScope.launch {
            val granted = client.permissionController
                .getGrantedPermissions(permissionsSet)
            if (granted.containsAll(permissionsSet)) {
                // Permissions already granted
                onPermissionAvailable(client)
            } else {
                // Permissions not granted, request permissions.
                requestPermissions.launch(permissionsSet)
            }
        }
    }

    private suspend fun onPermissionAvailable(client: HealthConnectClient) {
        readDailyRecords(client)
    }

    private suspend fun readDailyRecords(client: HealthConnectClient) {

        val today = ZonedDateTime.now()
        val startOfDay = today.truncatedTo(ChronoUnit.DAYS)
        val timeRangeFilter = TimeRangeFilter.between(
            startOfDay.toLocalDateTime(),
            today.toLocalDateTime()
        )


        val stepsRecordRequest = ReadRecordsRequest(StepsRecord::class, timeRangeFilter)
        val numberOfStepsToday = client.readRecords(stepsRecordRequest)
            .records
            .sumOf { it.count }


        val exerciseSessionRecordRequest = ReadRecordsRequest(
            ExerciseSessionRecord::class,
            timeRangeFilter
        )
        val exercises = client.readRecords(exerciseSessionRecordRequest).records

        Log.e("HEALTH", "$numberOfStepsToday $exercises")
    }

}