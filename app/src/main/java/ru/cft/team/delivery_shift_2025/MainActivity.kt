package ru.cft.team.delivery_shift_2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ru.cft.team.calculation.data.repository.CalculationRepositoryImpl
import ru.cft.team.calculation.data.service.CalculationService
import ru.cft.team.calculation.domain.GetPackageTypesUseCaseImpl
import ru.cft.team.calculation.domain.GetPointsUseCaseImpl
import ru.cft.team.calculation.presentation.CalculationViewModel
import ru.cft.team.calculation.ui.CalculationScreen
import ru.cft.team.delivery_shift_2025.ui.theme.Deliveryshift2025Theme
import ru.cft.team.network.client.KtorClient

class MainActivity : ComponentActivity() {
    // Позже будем получать с DI
    private val ktorClient = KtorClient()
    private val calculationService = CalculationService(ktorClient)
    private val calculationRepository = CalculationRepositoryImpl(calculationService)
    private val getPointsUseCase = GetPointsUseCaseImpl(calculationRepository)
    private val getPackageTypesUseCase = GetPackageTypesUseCaseImpl(calculationRepository)
    private val viewModel = CalculationViewModel(getPointsUseCase, getPackageTypesUseCase)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Deliveryshift2025Theme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                ) {
                    Box(Modifier.padding(it)) {
                        CalculationScreen(viewModel) { }
                    }
                }
            }
        }
    }
}