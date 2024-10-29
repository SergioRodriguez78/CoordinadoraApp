package com.coordinadora.coordinadoraapp.onboarding.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coordinadora.coordinadoraapp.core.ScreenState
import com.coordinadora.coordinadoraapp.onboarding.home.data.ImageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val service: ImageService
) : ViewModel() {

    private var _image = MutableStateFlow("")
    val image = _image.asStateFlow()

    private var _state: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.None)
    val state = _state.asStateFlow()

    fun getData(){
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { ScreenState.Loading }
            service.getImage(
                onResponse = { response ->
                    if(!response.isError){
                        _image.update { response.toString() }

                    }
                },
                onError = {
                    it.printStackTrace()
                }
            )
        }
    }
}
