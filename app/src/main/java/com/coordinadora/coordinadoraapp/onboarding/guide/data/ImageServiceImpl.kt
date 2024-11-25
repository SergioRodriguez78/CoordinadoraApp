package com.coordinadora.coordinadoraapp.onboarding.guide.data

import com.android.volley.Request
import com.coordinadora.coordinadoraapp.network.client.BaseApiClient
import com.coordinadora.coordinadoraapp.onboarding.guide.model.dto.ImageResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ImageServiceImpl(
    private val apiClient: BaseApiClient,
) : ImageService {
    override suspend fun getImage(): Result<ImageResponseDto> =
        withContext(Dispatchers.IO) {
            suspendCoroutine { continuation ->

                val jsonBody = JSONObject()

                apiClient.sendRequest(
                    method = Request.Method.GET,
                    url = URL,
                    jsonRequest = jsonBody,
                    onResponse = { response ->
                        val imageResponse =
                            Json.decodeFromString<ImageResponseDto>(response.toString())
                        continuation.resume(Result.success(imageResponse))
                    },
                    onError = { error ->
                        continuation.resumeWithException(error)
                    }
                )
            }
        }

    companion object {
        const val URL = "/obtenerimagen/"
    }
}