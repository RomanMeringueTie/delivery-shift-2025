package ru.cft.team.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Point(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double
)