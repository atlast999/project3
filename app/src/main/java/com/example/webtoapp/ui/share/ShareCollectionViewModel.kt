package com.example.webtoapp.ui.share

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.model.ShareMyWebAppListRequest
import com.example.webtoapp.repository.ICloudRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareCollectionViewModel @Inject constructor(application: Application) :
    BaseViewModel(application) {

    @Inject
    lateinit var repository: ICloudRepository

    val stateCollectionName = MutableStateFlow("")
    fun onSubmit() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.shareMyList(
                ShareMyWebAppListRequest(
                    collectionName = stateCollectionName.value
                )
            )
            navigateUp()
        }
    }
}