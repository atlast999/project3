package com.example.webtoapp.ui.newapp

import com.example.webtoapp.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NewAppViewModel @Inject constructor() : BaseViewModel() {
    val stateAppName = MutableStateFlow("")
    val stateAppUrl = MutableStateFlow("")
    val stateAppImage = MutableStateFlow<String?>(null)
}