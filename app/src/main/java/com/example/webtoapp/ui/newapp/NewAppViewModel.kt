package com.example.webtoapp.ui.newapp

import android.app.Application
import com.example.webtoapp.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewAppViewModel @Inject constructor(application: Application) : BaseViewModel(application) {
}