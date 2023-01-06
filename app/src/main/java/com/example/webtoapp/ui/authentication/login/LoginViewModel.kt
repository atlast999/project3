package com.example.webtoapp.ui.authentication.login

import android.app.Application
import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.model.AuthenticationRequest
import com.example.webtoapp.repository.ICloudRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var repository: ICloudRepository

    val stateUsername = MutableStateFlow("hoan1")
    val statePassword = MutableStateFlow("hoan123")
    fun onLogin() {
        runCoroutineTask {
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