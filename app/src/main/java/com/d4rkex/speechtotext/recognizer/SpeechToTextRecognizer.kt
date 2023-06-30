package com.d4rkex.speechtotext.recognizer

import com.d4rkex.speechtotext.recognizer.impl.RecognizerEvent
import kotlinx.coroutines.flow.StateFlow

interface SpeechToTextRecognizer {

    val data: StateFlow<RecognizerEvent>

    fun updateLocaleModel(locale: String)

    fun startListening()

    fun endListening()

    fun destroy()
}