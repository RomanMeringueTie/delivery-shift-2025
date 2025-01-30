package ru.cft.team.calculation.data.repository

import ru.cft.team.calculation.data.service.CalculationService
import ru.cft.team.network.model.PackageType
import ru.cft.team.network.model.Point

class CalculationRepositoryImpl(private val calculationService: CalculationService): CalculationRepository {
    override suspend fun getPoints(): List<Point> {
        return calculationService.getPoints()
    }

    override suspend fun getPackageTypes(): List<PackageType> {
        return calculationService.getPackageTypes()
    }
}