package com.example.webtoapp.ui.collections.detail

import android.app.Application
import com.example.webtoapp.base.adapter.pagingFlow
import com.example.webtoapp.base.domain.PagingRequest
import com.example.webtoapp.base.viewmodel.BaseViewModel
import com.example.webtoapp.base.viewmodel.navArgs
import com.example.webtoapp.repository.ICloudRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(application: Application) :
    BaseViewModel(application) {

    private val navArgs by navArgs<CollectionDetailFragmentArgs>()

    @Inject
    lateinit var repository: ICloudRepository

    val searchQuery = MutableStateFlow("")

    val webAppDataFlow = pagingFlow(
        request = PagingRequest(), fetchBy = { request ->
            repository.getCollectionDetail(navArgs.collection, request)
        }, invalidationFlows = listOf(
            searchQuery,
        )
    )

    fun takeCollection() {
        runCoroutineTask {
            repository.takeCollection(navArgs.collection)
            navigate(CollectionDetailFragmentDirections.popToHome())
        }
    }

}