package com.example.webtoapp.ui.collections.detail

import android.app.Application
import com.example.webtoapp.base.adapter.pagingFlow
import com.example.webtoapp.base.domain.PagingRequest
import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(application: Application) :
    BaseViewModel(application) {
    @Inject
    lateinit var repository: Repository

    val searchQuery = MutableStateFlow("")

    val webAppDataFlow = pagingFlow(
        request = PagingRequest(), fetchBy = { request ->
            repository.fetchAppList(request)
        }, invalidationFlows = listOf(
            searchQuery,
        )
    )

}