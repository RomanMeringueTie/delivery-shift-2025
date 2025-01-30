package ru.cft.team.calculation.domain

import ru.cft.team.network.model.Point

interface GetPointsUseCase {
    suspend operator fun invoke(): Result<List<Point>>
}
