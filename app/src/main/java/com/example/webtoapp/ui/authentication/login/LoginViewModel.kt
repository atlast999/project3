package com.example.webtoapp.ui.authentication.login

import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.model.AuthenticationRequest
import com.example.webtoapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var repository: Repository

    val stateUsername = MutableStateFlow("hoan1")
    val statePassword = MutableStateFlow("hoan123")

    /**
     * The function performs the login use case
     * by running a coroutine to handle asynchronous network task
     * @see Repository.login
     */
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