package com.coordinadora.coordinadoraapp.network.client

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class VolleyClient(context: Context): BaseApiClient {
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    override fun sendRequest(
        method: Int,
        url: String,
        jsonRequest: JSONObject?,
        onResponse: (JSONObject) -> Unit,
        onError: (Exception) -> Unit
    ) {
        val request = JsonObjectRequest(
            /* method = */ method,
            /* url = */ baseUrl + url,
            /* jsonRequest = */ jsonRequest,
            /* listener = */ { response ->
                onResponse(response) // Callback de éxito
            },
            /* errorListener = */ { error ->
                onError(error) // Callback de error
            }
        )

        requestQueue.add(request)
    }

    companion object {
        val baseUrl = "https://noderedtest.coordinadora.com/api/v1"
    }
}
