package io.github.luoshenshi.network

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

internal class NetworkClient {
    private val client: OkHttpClient = OkHttpClient()

    fun fetchData(url: String, headers: Map<String, String>, body: String): String? {
        val requestBody = body.toRequestBody("application/json".toMediaTypeOrNull())
        val requestBuilder = Request.Builder().url(url).post(requestBody)

        headers.forEach { (key, value) -> requestBuilder.addHeader(key, value) }

        val request = requestBuilder.build()

        client.newCall(request).execute().use { response ->
            return response.body?.string()
        }
    }
}
