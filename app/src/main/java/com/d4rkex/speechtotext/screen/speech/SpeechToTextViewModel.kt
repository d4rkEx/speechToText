package com.d4rkex.speechtotext.screen.speech

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d4rkex.speechtotext.recognizer.SpeechToTextRecognizer
import com.d4rkex.speechtotext.recognizer.impl.RecognizerEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class SpeechToTextViewModel(
    private val speechRecognizer: SpeechToTextRecognizer
) : ViewModel() {

    private val _state = MutableStateFlow(SpeechToTextState())
    val state = _state.asStateFlow()

    init {
        subscribeOnRecognitionEvent()
    }

    override fun onCleared() {
        speechRecognizer.destroy()
    }

    fun onSpeakClick() {
        when (speechRecognizer.data.value) {
            RecognizerEvent.OnReady -> speechRecognizer.startListening()
            is RecognizerEvent.OnResults -> speechRecognizer.startListening()
            is RecognizerEvent.OnError -> speechRecognizer.startListening()
            else -> speechRecognizer.endListening()
        }
    }

    fun requestPermission(
        context: Context,
        permissionLauncher: ManagedActivityResultLauncher<String, Boolean>
    ) {
        val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
        if (permissionCheckResult != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)

        } else {
            _state.update {
                it.copy(
                    isSpeakButtonEnabled = true
                )
            }
        }
    }

    fun onPermissionResult(isGranted: Boolean) {
        _state.update {
            it.copy(
                isSpeakButtonEnabled = isGranted
            )
        }
    }

    fun onLocaleChange(locale: String) {
        speechRecognizer.updateLocaleModel(locale)
        _state.update {
            it.copy(
                locale = locale
            )
        }
    }

    private fun subscribeOnRecognitionEvent() {
        speechRecognizer
            .data
            .onEach { event ->
                Log.d("testRcg", "OnRecEvent = ${event::class.simpleName}")
                when (event) {
                    RecognizerEvent.Idle -> {
                        _state.update {
                            it.copy(
                                buttonText = SpeechToTextState.RecognitionButtonText.Idle,
                            )
                        }
                    }

                    is RecognizerEvent.OnDeviceUnavailable -> {
                        _state.update {
                            it.copy(
                                buttonText = SpeechToTextState.RecognitionButtonText.Unavailable,
                                isSpeakButtonEnabled = false
                            )
                        }
                    }

                    RecognizerEvent.OnReady -> {
                        _state.update {
                            it.copy(
                                buttonText = SpeechToTextState.RecognitionButtonText.OnReady,
                            )
                        }
                    }

                    RecognizerEvent.OnRecording -> {
                        _state.update {
                            it.copy(
                                buttonText = SpeechToTextState.RecognitionButtonText.Recording,
                            )
                        }
                    }

                    is RecognizerEvent.OnResults -> {
                        _state.update {
                            it.copy(
                                buttonText = SpeechToTextState.RecognitionButtonText.OnResult,
                                translatedText = event.data
                            )
                        }
                    }

                    is RecognizerEvent.OnError -> {
                        _state.update {
                            it.copy(
                                buttonText = SpeechToTextState.RecognitionButtonText.Error,
                                translatedText = "Error. Code: ${event.code}"
                            )
                        }
                    }

                    RecognizerEvent.OnEnd -> {
                        _state.update {
                            it.copy(
                                buttonText = SpeechToTextState.RecognitionButtonText.End,
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}