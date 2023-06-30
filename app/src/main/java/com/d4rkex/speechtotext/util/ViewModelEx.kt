package com.d4rkex.speechtotext.util

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.d4rkex.speechtotext.di.DependenciesContainer
import com.d4rkex.speechtotext.di.ServiceLocator

@Composable
inline fun <reified VM : ViewModel> viewModel(
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    },
    key: String? = null,
): VM {
    val container = DependenciesContainer.current
    return viewModelFactory(
        viewModelStoreOwner = viewModelStoreOwner,
        key = key,
        dependencyContainer = container
    )
}

@Composable
inline fun <reified VM : ViewModel> viewModelFactory(
    viewModelStoreOwner: ViewModelStoreOwner,
    key: String?,
    dependencyContainer: ServiceLocator
): VM {
    return androidx.lifecycle.viewmodel.compose.viewModel(
        modelClass = VM::class.java,
        key = key,
        viewModelStoreOwner = viewModelStoreOwner,
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return dependencyContainer.createViewModel<VM>() as T
            }
        },
    )
}