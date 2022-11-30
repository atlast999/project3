package com.example.webtoapp.repository

import com.example.webtoapp.base.domain.PagingModel
import com.example.webtoapp.base.domain.PagingRequest
import com.example.webtoapp.model.WebAppInstance
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor() {
    suspend fun fetchAppList(params: PagingRequest): PagingModel<WebAppInstance> {
        delay(500)
        return params.run {
            page.times(size).let { start ->
                start.until(start + size)
            }
        }.map { index ->
            WebAppInstance(
                id = index.toString(),
                url = "google.com",
                image = "fake",
                name = "item number $index (from page ${params.page})"
            )
        }.let { data ->
            PagingModel(
                page = params.page,
                size = params.size,
                total = 1000,
                data = data
            )
        }
    }
}