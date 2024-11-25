package com.coordinadora.coordinadoraapp.onboarding.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coordinadora.coordinadoraapp.database.dao.UserDao
import com.coordinadora.coordinadoraapp.navigation.routes.CoordinadoraRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    private var _navigateToRoute = MutableStateFlow<CoordinadoraRoutes?>(null)
    val navigateToRoute = _navigateToRoute.asStateFlow()


    fun validateSession() {
        viewModelScope.launch {
            userDao.getUser().collect {
                if (it == null) {
                    _navigateToRoute.update {
                        CoordinadoraRoutes.Login
                    }
                } else {
                    _navigateToRoute.update {
                        CoordinadoraRoutes.Home
                    }
                }
            }
        }
    }
}
