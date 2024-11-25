package com.coordinadora.coordinadoraapp.database.mapper

import com.coordinadora.coordinadoraapp.database.entity.User
import com.coordinadora.coordinadoraapp.onboarding.login.model.dto.AuthenticationResponseDto

fun AuthenticationResponseDto.toUser(username: String): User {
    return User(
        username = username,
        data = this.data,
        validationPeriod = this.periodValidation
    )
}
