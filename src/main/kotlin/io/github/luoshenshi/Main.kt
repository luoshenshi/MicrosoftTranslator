package io.github.luoshenshi

import io.github.luoshenshi.translator.Translator
import io.github.luoshenshi.translator.TranslatorLocale

private fun main() {
    val translator = Translator()
    val text = "Bienvenido a MicrosoftTranslator!"

    val from = TranslatorLocale.AUTO_DETECT
    val to = TranslatorLocale.EN

    val translatedText = translator.translate(text, from, to)

    println(translatedText)
}