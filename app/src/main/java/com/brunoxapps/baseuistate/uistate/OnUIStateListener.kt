package com.brunoxapps.baseuistate.uistate

import androidx.compose.runtime.Composable

interface OnUIStateListener<T> {

    @Composable
    fun Loading()

    @Composable
    fun Success(data: T)

    @Composable
    fun Error(error: Throwable?)
}