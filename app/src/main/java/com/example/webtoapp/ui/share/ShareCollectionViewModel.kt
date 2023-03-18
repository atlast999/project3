package com.example.webtoapp.ui.share

import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.model.ShareMyWebAppListRequest
import com.example.webtoapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ShareCollectionViewModel @Inject constructor() :
    BaseViewModel() {

    @Inject
    lateinit var repository: Repository

    val stateCollectionName = MutableStateFlow("")
    fun onSubmit() {
        runCoroutineTask {
            repository.shareMyList(
                ShareMyWebAppListRequest(
                    collectionName = stateCollectionName.value
                )
            )
            navigateUp()
        }
    }
}