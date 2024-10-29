package com.coordinadora.coordinadoraapp.core

sealed interface ScreenState {
    data object Loading : ScreenState
    data object Success : ScreenState
    data object Error : ScreenState
    data object None : ScreenState
}
