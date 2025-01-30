package ru.cft.team.calculation.presentation

import ru.cft.team.network.model.PackageType
import ru.cft.team.network.model.Point

data class CalculationScreenState(
    var globalState: Boolean = false,
    val packageTypesState: PackageTypesState = PackageTypesState.Loading,
    val pointsState: PointsState = PointsState.Loading,
    val sendCityExtended: Boolean = false,
    val receiveCityExtended: Boolean = false,
    val packageTypeExtended: Boolean = false,
    val pickedSendCity: Point = Point(-1, "", -1.0, -1.0),
    val pickedReceiveCity: Point = Point(-1, "", -1.0, -1.0),
    val pickedPackageType: PackageType = PackageType(-1, "", -1, -1, -1, -1)
) {
    fun setGlobalState() {
        if (packageTypesState is PackageTypesState.Content && pointsState is PointsState.Content)
            globalState = true
    }
}

sealed interface PackageTypesState {
    data object Loading : PackageTypesState
    data class Content(val list: List<PackageType>) : PackageTypesState
    data class Failure(val message: String) : PackageTypesState
}

sealed interface PointsState {
    data object Loading : PointsState
    data class Content(val list: List<Point>) : PointsState
    data class Failure(val message: String) : PointsState
}