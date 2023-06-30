package com.d4rkex.speechtotext.screen.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.d4rkex.speechtotext.screen.speech.SpeechToTextScreen
import com.d4rkex.speechtotext.ui.theme.TextToSpeechTheme

@Composable
fun App() {
    TextToSpeechTheme {
        SpeechToTextScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}