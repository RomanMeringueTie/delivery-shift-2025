package ru.cft.team.calculation.domain

import ru.cft.team.network.model.PackageType


interface GetPackageTypesUseCase {
    suspend operator fun invoke(): Result<List<PackageType>>
}