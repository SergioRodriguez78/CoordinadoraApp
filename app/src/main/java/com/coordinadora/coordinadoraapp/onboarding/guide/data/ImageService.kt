package com.coordinadora.coordinadoraapp.onboarding.guide.data

import com.coordinadora.coordinadoraapp.onboarding.guide.model.dto.ImageResponseDto

interface ImageService {
    fun getImage(
        onResponse: (ImageResponseDto) -> Unit,
        onError: (Exception) -> Unit
    )
}
