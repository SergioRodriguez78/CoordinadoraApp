package com.coordinadora.coordinadoraapp.onboarding.home.data

import com.coordinadora.coordinadoraapp.onboarding.home.model.dto.ImageResponseDto

interface ImageService {
    fun getImage(
        onResponse: (ImageResponseDto) -> Unit,
        onError: (Exception) -> Unit
    )
}
