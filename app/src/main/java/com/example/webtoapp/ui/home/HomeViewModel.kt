package com.example.webtoapp.ui.home

import android.app.Application
import com.example.webtoapp.base.adapter.pagingFlow
import com.example.webtoapp.base.domain.PagingRequest
import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(application: Application) : BaseViewModel(application) {

    @Inject
    lateinit var repository: Repository

    val searchQuery = MutableStateFlow("adf")
    val webAppDataFlow = pagingFlow(
        request = PagingRequest(),
        fetchBy = { request ->
            repository.fetchAppList(request)
        }
    )
}

//            PagingModel(
//                page = index,
//                total = 2,
//                data = listOf(
//                    WebAppInstance(
//                        id = "1",
//                        url = "https://www.google.com/",
//                        image = "",
//                        name = "Google",
//                    ),
//                    WebAppInstance(
//                        id = "2",
//                        url = "https://voz.vn/",
//                        image = "",
//                        name = "VOZ",
//                    ),
//                    WebAppInstance(
//                        id = "3",
//                        url = "https://www.google.com/",
//                        image = "",
//                        name = "Google",
//                    ),
//                    WebAppInstance(
//                        id = "4",
//                        url = "https://voz.vn/",
//                        image = "",
//                        name = "VOZ",
//                    )
//                )
//            )