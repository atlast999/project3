package com.example.webtoapp.ui.home

import android.app.Application
import com.example.webtoapp.base.adapter.pagingFlow
import com.example.webtoapp.base.domain.PagingModel
import com.example.webtoapp.base.domain.PagingRequest
import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.model.WebAppInstance
import com.example.webtoapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var repository: Repository

    private val fakeData = MutableStateFlow(
        listOf(
            WebAppInstance(
                id = "1",
                url = "https://www.google.com/",
                image = "",
                name = "Google",
            ), WebAppInstance(
                id = "2",
                url = "https://voz.vn/",
                image = "",
                name = "VOZ",
            )
        )
    )

    val searchQuery = MutableStateFlow("")
    val webAppDataFlow = pagingFlow(
        request = PagingRequest(), fetchBy = { request ->
//            repository.fetchAppList(request.apply {
//
//            })
            return@pagingFlow PagingModel(
                page = 1,
                size = fakeData.value.size,
                data = fakeData.value.filter {
                    it.name.contains(searchQuery.value, ignoreCase = true)
                },
            )
        }, invalidationFlows = listOf(
            fakeData,
            searchQuery,
        )
    )

    fun onNewAppAdded(app: WebAppInstance) {
        fakeData.value = fakeData.value.toMutableList().apply {
            add(app.copy(id = size.toString()))
        }
    }
}