package com.example.webtoapp.ui.authentication.login

import android.app.Application
import com.example.webtoapp.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(application: Application) : BaseViewModel(application) {
    val stateUsername = MutableStateFlow("")
    val statePassword = MutableStateFlow("")
    fun onLogin() {

    }
}