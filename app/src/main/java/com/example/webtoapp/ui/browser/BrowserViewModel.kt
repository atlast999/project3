package com.example.webtoapp.ui.browser

import android.app.Application
import com.example.webtoapp.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BrowserViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

}