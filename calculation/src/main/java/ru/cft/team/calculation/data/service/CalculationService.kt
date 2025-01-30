package ru.cft.team.calculation.data.service

import ru.cft.team.network.client.KtorClient

class CalculationService(private val client: KtorClient) {
    suspend fun getPoints() = client.getPoints().points
    suspend fun getPackageTypes() = client.getPackageTypes().packages
}