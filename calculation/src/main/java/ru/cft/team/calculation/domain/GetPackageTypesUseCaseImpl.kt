package ru.cft.team.calculation.domain

import ru.cft.team.calculation.data.repository.CalculationRepository
import ru.cft.team.network.model.PackageType

class GetPackageTypesUseCaseImpl(private val calculationRepository: CalculationRepository) :
    GetPackageTypesUseCase {
    override suspend fun invoke(): Result<List<PackageType>> {
        return try {
            val result = calculationRepository.getPackageTypes()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}