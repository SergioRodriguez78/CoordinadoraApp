package com.coordinadora.coordinadoraapp.network.client

import org.json.JSONObject

interface BaseApiClient {
    fun sendRequest(
        method: Int,
        url: String,
        jsonRequest: JSONObject?,
        onResponse: (JSONObject) -> Unit,
        onError: (Exception) -> Unit
    )
}
