package com.brunoxapps.baseuistate.uistate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> StateFlow<UIState<T>>.SetUIStateListener(
    listener: OnUIStateListener<T>
) {

    val state by collectAsStateWithLifecycle()

    when (state) {
        is UIState.Loading -> {
            listener.Loading()
        }

        is UIState.Success -> {
            listener.Success((state as UIState.Success<T>).data)
        }

        is UIState.Error -> {
            listener.Error((state as UIState.Error).error)
        }
    }
}

@Composable
fun <T> StateFlow<UIState<T>>.CollectUIState(
    onLoading: @Composable () -> Unit,
    onSuccess: @Composable (T) -> Unit,
    onError: @Composable (Throwable) -> Unit,
) {
    val state by collectAsStateWithLifecycle()

    when (state) {
        is UIState.Loading -> {
            onLoading()
        }

        is UIState.Success -> {
            onSuccess((state as UIState.Success<T>).data)
        }

        is UIState.Error -> {
            onError((state as UIState.Error).error)
        }
    }
}