package com.d4rkex.speechtotext.screen.speech

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.d4rkex.speechtotext.util.viewModel

@Composable
fun SpeechToTextScreen(
    modifier: Modifier = Modifier,
    vm: SpeechToTextViewModel = viewModel()
) {
    val state by vm.state.collectAsState()
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = vm::onPermissionResult
    )

    Box(
        modifier = modifier,
    ) {
        SpeechToTextNode(
            modifier = Modifier.fillMaxSize(),
            onSpeakClick = vm::onSpeakClick,
            isPermissionGranted = state.isSpeakButtonEnabled,
            buttonText = state.buttonText.status,
            translatedText = state.translatedText,
            locale = state.locale,
            onLocaleChange = vm::onLocaleChange
        )
    }

    LaunchedEffect(key1 = Unit) {
        vm.requestPermission(context, permissionLauncher)
    }
}