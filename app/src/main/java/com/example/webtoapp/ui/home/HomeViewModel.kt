package com.example.webtoapp.ui.home

import android.app.Application
import com.example.webtoapp.base.adapter.pagingFlow
import com.example.webtoapp.base.domain.PagingRequest
import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.model.WebAppInstance
import com.example.webtoapp.repository.ICloudRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var repository: ICloudRepository

    private val flowValidatePage = MutableSharedFlow<Unit>()

    val searchQuery = MutableStateFlow("")
    val webAppDataFlow = pagingFlow(
        request = PagingRequest(),
        fetchBy = { request ->
            repository.getMyList(request)
        },
        invalidationFlows = listOf(
            searchQuery,
            flowValidatePage,
        )
    )

    fun onNewAppAdded(app: WebAppInstance) {
        runCoroutineTask {
            repository.createWebApp(app)
            flowValidatePage.emit(Unit)
        }
    }
}