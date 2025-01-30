package ru.cft.team.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PackageTypeResponseModel(
    val success: Boolean,
    val reason: String? = null,
    val packages: List<PackageType>
)