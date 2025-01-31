package ru.cft.team.delivery_shift_2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.cft.team.calculation.navigation.CalculationRoute
import ru.cft.team.calculation.ui.CalculationScreen
import ru.cft.team.delivery_shift_2025.navigation.BottomBar
import ru.cft.team.delivery_shift_2025.ui.theme.Deliveryshift2025Theme
import ru.cft.team.history.navigation.HistoryRoute
import ru.cft.team.profile.navigation.ProfileRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Deliveryshift2025Theme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    bottomBar = { BottomBar(navController = navController) }
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = CalculationRoute,
                        modifier = Modifier.padding(it)
                    ) {
                        composable<CalculationRoute> {
                            CalculationScreen(viewModel = hiltViewModel()) { }
                        }
                        composable<HistoryRoute> {
                            Text(text = "История")
                        }
                        composable<ProfileRoute> {
                            Text(text = "Профиль")
                        }
                    }
                }
            }
        }
    }
}