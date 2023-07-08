package com.brunoxapps.baseuistate

import androidx.lifecycle.viewModelScope
import com.brunoxapps.baseuistate.uistate.UIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<List<String>>() {

    private val mockRepository = listOf(
        "uno", "dos", "tres"
    )

    init {
        viewModelScope.launch {

            try {
                _uiState.value = UIState.Loading

                delay(1500)

                _uiState.value = UIState.Success(mockRepository)

            } catch (e: Exception) {
                _uiState.value = UIState.Error(e)
            }
        }
    }
}