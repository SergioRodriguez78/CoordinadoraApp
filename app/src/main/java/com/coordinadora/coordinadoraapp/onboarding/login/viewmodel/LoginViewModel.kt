package com.coordinadora.coordinadoraapp.onboarding.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coordinadora.coordinadoraapp.core.ScreenState
import com.coordinadora.coordinadoraapp.database.dao.UserDao
import com.coordinadora.coordinadoraapp.database.mapper.toUser
import com.coordinadora.coordinadoraapp.onboarding.login.data.remote.AuthenticationService
import com.coordinadora.coordinadoraapp.onboarding.login.model.dto.AuthenticationResponseDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val service: AuthenticationService,
    private val dao: UserDao
) : ViewModel() {

    private var _username = MutableStateFlow("")
    val username = _username.asStateFlow()

    private var _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private var _state: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.None)
    val screenState = _state.asStateFlow()

    fun updateUsername(username: String) {
        _username.update { username }
    }

    fun updatePassword(password: String) {
        _password.update { password }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.update { ScreenState.Loading }
                service.login(
                    username = username,
                    password = password,
                    onResponse = { response ->
                        if (!response.isError) {
                            _state.update { ScreenState.Success }

                            saveUser(username = username, dto = response)

                        } else {
                            _state.update { ScreenState.Error }
                        }
                    },
                    onError = { error ->
                        error.printStackTrace()
                        _state.update { ScreenState.Error }
                    }
                )
            } catch (e: Exception) {
                _state.update { ScreenState.Error }
            }
        }
    }

    private fun saveUser(username: String, dto: AuthenticationResponseDto) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.save(dto.toUser(username))
        }
    }
}
