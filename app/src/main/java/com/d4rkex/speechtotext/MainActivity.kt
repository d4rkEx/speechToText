package com.d4rkex.speechtotext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.d4rkex.speechtotext.di.DependenciesContainer
import com.d4rkex.speechtotext.di.ServiceLocator
import com.d4rkex.speechtotext.screen.app.App

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(
                DependenciesContainer provides ServiceLocator(this)
            ) {
                App()
            }
        }
    }
}