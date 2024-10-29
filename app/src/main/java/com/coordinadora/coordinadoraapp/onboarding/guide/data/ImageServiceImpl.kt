package com.coordinadora.coordinadoraapp.onboarding.guide.data

import com.android.volley.Request
import com.coordinadora.coordinadoraapp.network.client.BaseApiClient
import com.coordinadora.coordinadoraapp.onboarding.guide.model.dto.ImageResponseDto
import kotlinx.serialization.json.Json
import org.json.JSONObject

class ImageServiceImpl(
    private val apiClient: BaseApiClient,
) : ImageService {
    override fun getImage(
        onResponse: (ImageResponseDto) -> Unit,
        onError: (Exception) -> Unit
    ) {

        val jsonBody = JSONObject()

        apiClient.sendRequest(
            method = Request.Method.GET,
            url = URL,
            jsonRequest = jsonBody,
            onResponse = { response ->
                val imageResponse = Json.decodeFromString<ImageResponseDto>(response.toString())
                onResponse(imageResponse)
            },
            onError = { error ->
                error.printStackTrace()
            }
        )
    }

    companion object {
        const val URL = "/obtenerimagen/"
    }
}