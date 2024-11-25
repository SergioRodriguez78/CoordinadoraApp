package com.coordinadora.coordinadoraapp.onboarding.login.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationResponseDto(
    val isError: Boolean = false,
    val data: String = "",
    @SerialName("perido_validacion") val periodValidation: Int = 0,
    val message: String? = null
)
