package ru.cft.team.calculation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.cft.team.calculation.data.repository.CalculationRepository
import ru.cft.team.calculation.data.repository.CalculationRepositoryImpl
import ru.cft.team.calculation.domain.GetPackageTypesUseCase
import ru.cft.team.calculation.domain.GetPackageTypesUseCaseImpl
import ru.cft.team.calculation.domain.GetPointsUseCase
import ru.cft.team.calculation.domain.GetPointsUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class CalculationModule {

    @Binds
    abstract fun bindCalculationRepository(calculationRepositoryImpl: CalculationRepositoryImpl): CalculationRepository

    @Binds
    abstract fun bindGetPackageTypesUseCase(getPackageTypesUseCaseImpl: GetPackageTypesUseCaseImpl): GetPackageTypesUseCase

    @Binds
    abstract fun bindGetPointsUseCaseImpl(getPointsUseCaseImpl: GetPointsUseCaseImpl): GetPointsUseCase

}