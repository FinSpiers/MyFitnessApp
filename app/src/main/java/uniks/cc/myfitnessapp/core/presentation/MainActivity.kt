package uniks.cc.myfitnessapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import uniks.cc.myfitnessapp.core.presentation.navigation.NavigationHost
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.BottomNavigationBar
import uniks.cc.myfitnessapp.core.presentation.navigation.navigationbar.NavigationBarState
import uniks.cc.myfitnessapp.ui.theme.MyFitnessAppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor() : ComponentActivity() {
    @Inject
    lateinit var bottomNavigationDestinations: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = hiltViewModel()
            val navController = rememberNavController()
            viewModel.setNavController(navController)
            val navBarState = viewModel.navBarState.value

            MyFitnessAppTheme {
                Column {
                    NavigationHost(
                        navController = navController,
                        startDestination = navBarState.currentRoute,
                        bottomNavigationDestinations = bottomNavigationDestinations
                    )
                    BottomNavigationBar(navBarState = navBarState, onEvent = viewModel::onEvent)
                }
            }
        }
    }
}