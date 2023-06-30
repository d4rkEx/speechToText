package com.d4rkex.speechtotext.recognizer.impl

import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.d4rkex.speechtotext.recognizer.SpeechToTextRecognizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DefaultRecognizer(
    private val recognizer: SpeechRecognizer,
    private val isDeviceAvailable: Boolean
) : SpeechToTextRecognizer {

    private val _data: MutableStateFlow<RecognizerEvent> = MutableStateFlow(RecognizerEvent.Idle)
    override val data: StateFlow<RecognizerEvent> = _data.asStateFlow()
    private val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        // Locales: BCP 47
        // https://www.techonthenet.com/js/language_tags.php
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ru-RU")
    }

    init {
        subscribeOnRecognitionEvent()
    }

    override fun updateLocaleModel(locale: String) {
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale)
    }

    override fun startListening() {
        recognizer.startListening(recognizerIntent)
        _data.update { RecognizerEvent.OnRecording }
    }

    override fun endListening() {
        recognizer.stopListening()
        _data.update { RecognizerEvent.OnEnd }
    }

    override fun destroy() {
        _data.update { RecognizerEvent.Idle }

        recognizer.setRecognitionListener(null)
        recognizer.destroy()
    }

    private fun subscribeOnRecognitionEvent() {
        if (isDeviceAvailable.not()) {
            _data.update { RecognizerEvent.OnDeviceUnavailable }
            return
        }

        _data.update { RecognizerEvent.OnReady }
        recognizer.setRecognitionListener(
            DefaultRecognitionListener(
                onResults = { bundle ->
                    val results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) ?: emptyList()
                    _data.update {
                        val result = if (results.isEmpty()) "" else results[0]
                        RecognizerEvent.OnResults(result)
                    }
                },
                onError = { code ->
                    _data.update { RecognizerEvent.OnError(code) }
                },
            )
        )
    }
}