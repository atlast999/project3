package com.example.webtoapp.ui.authentication.signup

import android.app.Application
import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.model.AuthenticationRequest
import com.example.webtoapp.repository.ICloudRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var repository: ICloudRepository

    val stateUsername = MutableStateFlow("")
    val statePassword = MutableStateFlow("")
    fun onSignup() {
        runCoroutineTask {
            repository.signup(
                AuthenticationRequest(
                    username = stateUsername.value,
                    password = statePassword.value,
                )
            )
            navigate(SignupFragmentDirections.toHomeMenu())
        }
    }
}