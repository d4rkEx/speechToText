package com.d4rkex.speechtotext.di

import android.os.Build
import android.speech.SpeechRecognizer
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.app.ComponentActivity
import androidx.lifecycle.ViewModel
import com.d4rkex.speechtotext.recognizer.impl.DefaultRecognizer
import com.d4rkex.speechtotext.screen.speech.SpeechToTextViewModel

val DependenciesContainer = staticCompositionLocalOf { ServiceLocator(null) }

class ServiceLocator(
    activity: ComponentActivity?
) {

    val context = activity?.applicationContext

    inline fun <reified VM : ViewModel> createViewModel(): ViewModel {
        if (context == null) error("ServiceLocator is not initialized")

        return when (VM::class) {
            SpeechToTextViewModel::class -> {
                val isDeviceAvailable = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    SpeechRecognizer.isOnDeviceRecognitionAvailable(context)
                } else {
                    SpeechRecognizer.isRecognitionAvailable(context)
                }
                SpeechToTextViewModel(
                    speechRecognizer = DefaultRecognizer(
                        recognizer = SpeechRecognizer.createSpeechRecognizer(context),
                        isDeviceAvailable = isDeviceAvailable
                    )
                )
            }

            else -> error("Unknown ViewModel class ${VM::class}")
        }
    }
}