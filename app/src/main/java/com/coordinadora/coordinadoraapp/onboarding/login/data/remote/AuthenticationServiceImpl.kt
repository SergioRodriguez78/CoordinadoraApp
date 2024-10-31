package com.coordinadora.coordinadoraapp.onboarding.login.data.remote

import com.android.volley.Request
import com.coordinadora.coordinadoraapp.network.client.BaseApiClient
import com.coordinadora.coordinadoraapp.onboarding.login.model.dto.AuthenticationResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthenticationServiceImpl(
    private val apiClient: BaseApiClient,
) : AuthenticationService {

    override suspend fun login(
        username: String,
        password: String,
    ) = withContext(Dispatchers.IO) {
        suspendCoroutine { continuation ->

            val jsonBody = JSONObject()
            jsonBody.put(USERNAME_KEY, username)
            jsonBody.put(PASSWORD_KEY, password)

            apiClient.sendRequest(
                method = Request.Method.POST,
                url = URL,
                jsonRequest = jsonBody,
                onResponse = { response ->
                    val authResponse =
                        Json.decodeFromString<AuthenticationResponseDto>(response.toString())
                    continuation.resume(Result.success(authResponse))
                },
                onError = { error ->
                    continuation.resumeWith(Result.failure(error))
                }
            )
        }
    }

    companion object {
        const val USERNAME_KEY = "usuario"
        const val PASSWORD_KEY = "password"
        const val URL = "/validacion-usuario/"
    }
}
