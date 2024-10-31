package com.coordinadora.coordinadoraapp.onboarding.login.data.remote

import com.coordinadora.coordinadoraapp.onboarding.login.model.dto.AuthenticationResponseDto

interface AuthenticationService {
    suspend fun login(
        username: String,
        password: String,
    ): Result<AuthenticationResponseDto>
}
