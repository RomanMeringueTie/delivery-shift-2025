package ru.cft.team.calculation.data.repository

import ru.cft.team.network.model.PackageType
import ru.cft.team.network.model.Point

interface CalculationRepository {
    suspend fun getPoints(): List<Point>
    suspend fun getPackageTypes(): List<PackageType>
}