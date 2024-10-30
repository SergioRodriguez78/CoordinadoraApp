package com.coordinadora.coordinadoraapp.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coordinadora.coordinadoraapp.database.dao.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoreViewmodel @Inject constructor(
    private val userDao: UserDao
) : ViewModel() {

    private var _forceLogout = MutableStateFlow(false)
    val forceLogout = _forceLogout.asStateFlow()

    fun observeUser() {
        viewModelScope.launch {
            userDao.getUser().collect { user ->
                println("User: $user, validationPeriod: ${user?.validationPeriod}")

                if (user?.validationPeriod == 0) {
                    manageUserWithoutValidationPeriod()
                }
            }
        }
    }

    fun dismissForceLogout() {
        viewModelScope.launch {
            _forceLogout.update {
                false
            }
        }
    }

    fun reduceValidationPeriod() {
        viewModelScope.launch {
            userDao.reduceValidationPeriod()
        }
    }

    private fun manageUserWithoutValidationPeriod() {
        viewModelScope.launch {
            userDao.delete()
            _forceLogout.update {
                true
            }
        }
    }

}
