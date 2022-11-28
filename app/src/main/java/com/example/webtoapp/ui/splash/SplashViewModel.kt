package com.example.webtoapp.ui.splash

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.webtoapp.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(application: Application): BaseViewModel(application) {

    override fun onReady() {
        super.onReady()
        viewModelScope.launch {
            delay(3000)
            navigate(
                SplashFragmentDirections.toHomeMenu()
            )
        }
    }
}