package ru.cft.team.calculation.domain

import ru.cft.team.calculation.data.repository.CalculationRepository
import ru.cft.team.network.model.Point
import javax.inject.Inject

class GetPointsUseCaseImpl @Inject constructor(private val calculationRepository: CalculationRepository) :
    GetPointsUseCase {
    override suspend fun invoke(): Result<List<Point>> {
        return try {
            val result = calculationRepository.getPoints()
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}