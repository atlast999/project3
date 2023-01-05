package com.example.webtoapp.ui.authentication.login

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.model.AuthenticationRequest
import com.example.webtoapp.repository.ICloudRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var repository: ICloudRepository

    val stateUsername = MutableStateFlow("")
    val statePassword = MutableStateFlow("")
    fun onLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.login(
                AuthenticationRequest(
                    username = stateUsername.value,
                    password = statePassword.value,
                )
            )
            navigate(LoginFragmentDirections.toHomeMenu())
        }
    }
}