package com.coordinadora.coordinadoraapp.onboarding.guide.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageResponseDto (
    val isError: Boolean,
    @SerialName("base64") val pdfEncoded: String,
    @SerialName("posiciones") val locations: List<LocationDto>
)
