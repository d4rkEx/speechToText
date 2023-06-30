package com.d4rkex.speechtotext.screen.speech

import androidx.compose.runtime.Immutable

@Immutable
data class SpeechToTextState(
    val isSpeakButtonEnabled: Boolean = false,
    val translatedText: String = "...",
    val locale: String = "ru-RU",
    val buttonText: RecognitionButtonText = RecognitionButtonText.Idle
) {

    enum class RecognitionButtonText(
        val status: String
    ) {
        Idle("Setting up"),
        Unavailable("Unavaiable"),
        OnReady("Speak"),
        Recording("Recording. Press to Stop"),
        Error("Error"),
        End("End"),
        OnResult("Result"),
    }
}
