package io.github.luoshenshi.translator

internal object Utils {
    private const val BASE_URL = "https://api.cognitive.microsofttranslator.com/"
    private const val ENDPOINT = "translate?"

    fun getUrl(from: String, to: String): String {
        val params = mapOf(
            "api-version" to "3.0",
            "from" to from,
            "to" to to
        ).map { "${it.key}=${it.value}" }
            .joinToString("&")

        return BASE_URL + ENDPOINT + params
    }
}