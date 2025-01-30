package ru.cft.team.calculation.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.cft.team.calculation.domain.GetPackageTypesUseCase
import ru.cft.team.calculation.domain.GetPointsUseCase

class CalculationViewModel(
    private val getPointsUseCase: GetPointsUseCase,
    private val getPackageTypesUseCase: GetPackageTypesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CalculationScreenState())
    val state: StateFlow<CalculationScreenState> = _state

    init {
        loadPoints()
        loadPackageTypes()
    }

    fun onAction(calculationScreenAction: CalculationScreenAction) {
        when (calculationScreenAction) {
            is CalculationScreenAction.ChangePackageTypeExtended -> {
                _state.value =
                    _state.value.copy(packageTypeExtended = !_state.value.packageTypeExtended)
            }

            is CalculationScreenAction.ChangeReceiveCityExtended -> {
                _state.value =
                    _state.value.copy(receiveCityExtended = !_state.value.receiveCityExtended)
            }

            is CalculationScreenAction.ChangeSendCityExtended -> {
                _state.value =
                    _state.value.copy(sendCityExtended = !_state.value.sendCityExtended)
            }

            is CalculationScreenAction.SetPickedPackageType -> {
                _state.value =
                    _state.value.copy(pickedPackageType = calculationScreenAction.packageType)
            }

            is CalculationScreenAction.SetPickedSendCity -> {
                _state.value =
                    _state.value.copy(pickedSendCity = calculationScreenAction.point)
            }

            is CalculationScreenAction.SetPickedReceiveCity -> {
                _state.value =
                    _state.value.copy(pickedReceiveCity = calculationScreenAction.point)
            }

            CalculationScreenAction.RetryLoad -> {
                loadPoints()
                loadPackageTypes()
            }
        }
    }

    private fun loadPoints() {
        if (_state.value.pointsState is PointsState.Loading) {
            viewModelScope.launch {
                val result = getPointsUseCase()
                result.fold(onSuccess = {
                    _state.value = _state.value.copy(
                        pointsState = PointsState.Content(it),
                        pickedSendCity = it.first(),
                        pickedReceiveCity = it[1]
                    )
                }, onFailure = {
                    _state.value = _state.value.copy(
                        pointsState = PointsState.Failure(it.localizedMessage ?: "Unknown Error")
                    )
                })
            }
        }
    }

    private fun loadPackageTypes() {
        if (_state.value.pointsState is PointsState.Loading) {
            viewModelScope.launch {
                val result = getPackageTypesUseCase()
                result.fold(onSuccess = {
                    _state.value = _state.value.copy(
                        packageTypesState = PackageTypesState.Content(it),
                        pickedPackageType = it.first()
                    )
                }, onFailure = {
                    _state.value = _state.value.copy(
                        packageTypesState = PackageTypesState.Failure(
                            it.localizedMessage ?: "Unknown Error"
                        )
                    )
                })
            }
        }
    }
}
