package uniks.cc.myfitnessapp.core.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.ActivityRecognition
import com.google.android.gms.location.ActivityRecognitionClient
import dagger.hilt.android.AndroidEntryPoint
import uniks.cc.myfitnessapp.core.domain.repository.CoreRepository
import uniks.cc.myfitnessapp.core.domain.util.ActivityTransitionReceiver
import uniks.cc.myfitnessapp.core.domain.util.ActivityTransitions
import uniks.cc.myfitnessapp.core.presentation.navigation.NavigationHost
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.BottomNavigationBar
import uniks.cc.myfitnessapp.feature_dashboard.domain.repository.WorkoutRepository
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {
    @Inject
    lateinit var workoutRepository: WorkoutRepository

    @Inject
    lateinit var coreRepository: CoreRepository

    lateinit var client: ActivityRecognitionClient

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        client = ActivityRecognition.getClient(this)

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
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            requestForUpdates()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestForUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        client
            .requestActivityTransitionUpdates(
                ActivityTransitions.getActivityTransitionRequest(),
                getPendingIntent()
            )
            .addOnSuccessListener {
                Log.e("ATU", "successful registration")
            }
            .addOnFailureListener {
                Log.e("ATU", "Unsuccessful registration")
            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun deregisterForUpdates() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        client
            .removeActivityTransitionUpdates(getPendingIntent())
            .addOnSuccessListener {
                getPendingIntent().cancel()
                Log.e("ATU", "successful deregistration")
            }
            .addOnFailureListener {
                Log.e("ATU", "unsuccessful deregistration")
            }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, ActivityTransitionReceiver::class.java)
        return PendingIntent.getBroadcast(
            this,
            122,
            intent,
            PendingIntent.FLAG_MUTABLE
        )
    }

}