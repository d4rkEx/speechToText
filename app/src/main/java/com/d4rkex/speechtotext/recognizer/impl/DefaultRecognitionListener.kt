package com.d4rkex.speechtotext.recognizer.impl

import android.os.Bundle
import android.speech.RecognitionListener

class DefaultRecognitionListener(
    private val onResults: (bundle: Bundle) -> Unit = {},
    private val onError: (code: Int) -> Unit = {},
) : RecognitionListener {

    override fun onReadyForSpeech(p0: Bundle?) = Unit

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(p0: Float) = Unit

    override fun onBufferReceived(p0: ByteArray?) = Unit

    override fun onEndOfSpeech() = Unit

    override fun onError(p0: Int) {
        onError.invoke(p0)
    }

    override fun onResults(bundle: Bundle?) {
        bundle?.let(onResults)
    }

    override fun onPartialResults(p0: Bundle?) = Unit

    override fun onEvent(p0: Int, p1: Bundle?) = Unit
}