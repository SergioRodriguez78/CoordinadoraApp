package com.coordinadora.coordinadoraapp.onboarding.login.data.remote

import com.coordinadora.coordinadoraapp.onboarding.login.model.dto.AuthenticationResponseDto

interface AuthenticationService {
    fun login(
        username: String,
        password: String,
        onResponse: (AuthenticationResponseDto) -> Unit,
        onError: (Exception) -> Unit
    )
}
