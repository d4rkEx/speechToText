package com.d4rkex.speechtotext.screen.speech

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpeechToTextNode(
    modifier: Modifier = Modifier,
    onSpeakClick: () -> Unit,
    isPermissionGranted: Boolean,
    buttonText: String,
    translatedText: String,
    locale: String,
    onLocaleChange: (String) -> Unit
) {
    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = locale,
                onValueChange = onLocaleChange
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = onSpeakClick,
                enabled = isPermissionGranted,
                content = {
                    Text(text = buttonText)
                }
            )
            Spacer(modifier = Modifier.height(18.dp))
            Text(text = translatedText)
        }
    }
}