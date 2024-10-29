package com.coordinadora.coordinadoraapp.onboarding.guide.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    @SerialName("latitud") val latitude: Double,
    @SerialName("longitud") val longitude: Double
)
