package com.coordinadora.coordinadoraapp.onboarding.login.data.remote

import com.android.volley.Request
import com.coordinadora.coordinadoraapp.network.client.BaseApiClient
import com.coordinadora.coordinadoraapp.onboarding.login.model.dto.AuthenticationResponseDto
import kotlinx.serialization.json.Json
import org.json.JSONObject

class AuthenticationServiceImpl(
    private val apiClient: BaseApiClient,
) : AuthenticationService {

    override fun login(
        username: String,
        password: String,
        onResponse: (AuthenticationResponseDto) -> Unit,
        onError: (Exception) -> Unit
    ) {

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
                onResponse(authResponse)
            },
            onError = { error ->
                error.printStackTrace()
            }
        )
    }

    companion object {
        const val USERNAME_KEY = "usuario"
        const val PASSWORD_KEY = "password"
        const val URL = "/validacion-usuario/"
    }
}
