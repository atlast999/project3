package com.example.webtoapp.ui.home

import android.app.Application
import com.example.webtoapp.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application): BaseViewModel(application) {
    val searchQuery = MutableStateFlow("")
}