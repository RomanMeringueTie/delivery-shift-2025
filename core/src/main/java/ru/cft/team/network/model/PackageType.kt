package ru.cft.team.network.model

import kotlinx.serialization.Serializable

@Serializable
data class PackageType(
    val id: Int,
    val name: String,
    val length: Int,
    val width: Int,
    val height: Int,
    val weight: Int,
)