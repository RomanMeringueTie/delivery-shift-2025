package ru.cft.team.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PointResponseModel(
    val success: Boolean,
    val reason: String? = null,
    val points: List<Point>
)