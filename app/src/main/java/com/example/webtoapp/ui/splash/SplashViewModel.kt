package com.example.webtoapp.ui.splash

import androidx.lifecycle.viewModelScope
import com.example.webtoapp.base.credential.ICredentialManager
import com.example.webtoapp.base.util.notNullOrEmptyLet
import com.example.webtoapp.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var credentialManager: ICredentialManager

    override fun onReady() {
        super.onReady()
        viewModelScope.launch {
            delay(500)
            navigate(
                credentialManager.getToken().notNullOrEmptyLet {
                    SplashFragmentDirections.toHomeMenu()
                } ?: SplashFragmentDirections.toLogin()
            )
        }
    }
}