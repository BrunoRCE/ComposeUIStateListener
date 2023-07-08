package com.brunoxapps.baseuistate

import androidx.lifecycle.ViewModel
import com.brunoxapps.baseuistate.uistate.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<T> : ViewModel() {
    protected val _uiState = MutableStateFlow<UIState<T>>(UIState.Loading)
    val uiState = _uiState.asStateFlow()
}