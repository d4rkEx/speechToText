package com.d4rkex.speechtotext.recognizer.impl

sealed class RecognizerEvent {

    object Idle : RecognizerEvent()

    object OnDeviceUnavailable : RecognizerEvent()

    object OnReady : RecognizerEvent()

    object OnRecording : RecognizerEvent()

    data class OnResults(val data: String) : RecognizerEvent()

    data class OnError(val code: Int) : RecognizerEvent()

    object OnEnd : RecognizerEvent()
}
