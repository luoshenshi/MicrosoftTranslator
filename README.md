# MicrosoftTranslator

A simple and easy-to-use translation library powered by Microsoft Translator.

---

## The Story Behind This Library's Broken Versions
Versions `1.0.0`, `1.0.1`, and `1.1.0` of this library don't work due to a combination of changes in Maven Central and my own reliance on outdated practices (plus a bit of laziness). It's a long story, but to keep it short: I stuck with legacy methods, Maven Central made some updates, and I ran into issues.

Also, it's been months since I last published anything (like my other projects on GitHub: pokedex, ytdl-android, etc.), so I kind of forgot how to publish properly. It’s a bit embarrassing, but I’ve learned from the experience.

---

## 📦 Implementation

### Kotlin
```kotlin
implementation("io.github.luoshenshi:MicrosoftTranslator:1.1.1")
```
### Groovy
```groovy
implementation 'io.github.luoshenshi:MicrosoftTranslator:1.1.1'
```
### Maven
```xml
<dependency>
    <groupId>io.github.luoshenshi</groupId>
    <artifactId>MicrosoftTranslator</artifactId>
    <version>1.1.1</version>
</dependency>
```
---
# 🚀 Usage
## Kotlin
```kotlin
val translator = Translator()
val text = "Bienvenido a MicrosoftTranslator!"

val from = TranslatorLocale.AUTO_DETECT
val to = TranslatorLocale.EN

val translatedText = translator.translate(text, from, to)

println(translatedText)
```
## Java
```java
Translator translator = new Translator();
String text = "Bienvenido a MicrosoftTranslator!";

String from = TranslatorLocale.AUTO_DETECT;
String to = TranslatorLocale.EN;

String translatedText = translator.translate(text, from, to);

System.out.println(translatedText);
```
---
Enjoy 😭