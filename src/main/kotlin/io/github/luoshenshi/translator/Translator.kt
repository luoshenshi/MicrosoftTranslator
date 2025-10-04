package io.github.luoshenshi.translator

import io.github.luoshenshi.network.NetworkClient
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Translator Class
 */
class Translator {

    private val networkClient = NetworkClient()

    /**
     * Translates the Text
     * instead of "from" you can also use AUTO_DETECT
     */
    fun translate(text: String, from: String, to: String): String {
        val url = Utils.getUrl(from, to)
        val xClientTraceId = clientTraceId()
        val xMTSignature = generateSignature(url)

        val headers = mapOf(
            "X-ClientTraceId" to xClientTraceId,
            "X-MT-Signature" to xMTSignature
        )

        val json = """[{"text": "${text.replace("\"", "\\\"")}"}]"""
        var translatedText = ""
        try {
            val translatedJson = networkClient.fetchData(url, headers, json)

            val regex = """"text"\s*:\s*"([^"]+)"""".toRegex()
            val match = regex.find(translatedJson.toString())

            translatedText = match?.groupValues?.get(1) ?: "Not Found"
        } catch (error: Exception) {
            if (error.message.equals("No such host is known (api.cognitive.microsofttranslator.com)")) {
                throw Exception("No Internet Connection")
            }
            throw Exception(error.message)
        }

        return translatedText
    }

    private fun clientTraceId(): String {
        return UUID.randomUUID().toString().replace(":", "").replace("[", "").replace("]", "").replace("<", "")
            .replace(">", "")
    }

    private fun generateSignature(url: String): String {
        var formattedUrl = url
        val uniqueId = UUID.randomUUID().toString().replace("-", "")

        formattedUrl = when {
            formattedUrl.startsWith("wss://", ignoreCase = true) -> formattedUrl.replace("wss://", "")
            formattedUrl.startsWith("https://", ignoreCase = true) -> formattedUrl.replace("https://", "")
            formattedUrl.startsWith("http://", ignoreCase = true) -> formattedUrl.replace("http://", "")
            else -> formattedUrl
        }.lowercase(Locale.getDefault())

        return try {
            val encodedUrl = URLEncoder.encode(formattedUrl, ENCODING)
            val timestamp = getTimestamp()
            val data = "MSTranslatorAndroidApp$encodedUrl$timestamp$uniqueId".lowercase()
                .toByteArray(Charsets.UTF_8)

            val secretKeySpec = SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), "HmacSHA256")
            val mac = Mac.getInstance("HmacSHA256").apply { init(secretKeySpec) }
            val hmacSignature = Base64.getEncoder().encodeToString(mac.doFinal(data))

            "MSTranslatorAndroidApp::$hmacSignature::$timestamp::$uniqueId"
        } catch (e: Exception) {
            println("Error creating HmacSignature: ${e.message}")
            e.printStackTrace()
            ""
        }
    }

    private fun getTimestamp(): String {
        val timestamp = System.currentTimeMillis() + 5000
        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }
        return dateFormat.format(Date(timestamp)).lowercase(Locale.getDefault()) + "gmt"
    }

    companion object {
        private const val ENCODING = "UTF-8"
        private const val SECRET_KEY =
            "oik6PdDdMnOXemTbwvMn9de/h9lFnfBaCWbGMMZqqoSaQaqUOqjVGm5NqsmjcBI1x+sS9ugjB55HEJWRiFXYFw=="
    }
}
