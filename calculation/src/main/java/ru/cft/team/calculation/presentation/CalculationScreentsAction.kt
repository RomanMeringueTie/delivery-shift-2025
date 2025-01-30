package ru.cft.team.calculation.presentation

import ru.cft.team.network.model.PackageType
import ru.cft.team.network.model.Point

sealed interface CalculationScreenAction {
    data object ChangeSendCityExtended : CalculationScreenAction
    data object ChangeReceiveCityExtended : CalculationScreenAction
    data object ChangePackageTypeExtended : CalculationScreenAction
    data class SetPickedSendCity(val point: Point) : CalculationScreenAction
    data class SetPickedReceiveCity(val point: Point) : CalculationScreenAction
    data class SetPickedPackageType(val packageType: PackageType) : CalculationScreenAction
    data object RetryLoad : CalculationScreenAction
}