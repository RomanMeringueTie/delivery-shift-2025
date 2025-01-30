package ru.cft.team.calculation.data.service

import ru.cft.team.network.network.KtorClient
import javax.inject.Inject

class CalculationService @Inject constructor(private val client: KtorClient) {
    suspend fun getPoints() = client.getPoints().points
    suspend fun getPackageTypes() = client.getPackageTypes().packages
}