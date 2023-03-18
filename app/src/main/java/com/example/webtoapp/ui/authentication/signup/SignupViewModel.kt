package com.example.webtoapp.ui.authentication.signup

import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.model.AuthenticationRequest
import com.example.webtoapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var repository: Repository

    val stateUsername = MutableStateFlow("hoan1")
    val statePassword = MutableStateFlow("hoan123")
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