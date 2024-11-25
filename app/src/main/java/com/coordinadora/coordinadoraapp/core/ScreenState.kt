package com.coordinadora.coordinadoraapp.core

sealed interface ScreenState {
    data object Loading : ScreenState
    data object Success : ScreenState
    data class Error(val message: String) : ScreenState
    data object None : ScreenState
}
