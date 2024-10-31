package com.coordinadora.coordinadoraapp.onboarding.guide.data

import com.coordinadora.coordinadoraapp.onboarding.guide.model.dto.ImageResponseDto

interface ImageService {
    suspend fun getImage(): Result<ImageResponseDto>
}
