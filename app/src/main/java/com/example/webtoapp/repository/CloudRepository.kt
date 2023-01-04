package com.example.webtoapp.repository

import com.example.webtoapp.base.domain.PagingModel
import com.example.webtoapp.base.domain.PagingRequest
import com.example.webtoapp.model.WebAppInstance
import com.example.webtoapp.repository.service.ApiService
import kotlinx.coroutines.delay

class CloudRepository(private val service: ApiService) : ICloudRepository {
    suspend fun fetchAppList(params: PagingRequest): PagingModel<WebAppInstance> {
        delay(500)
        if (params.page == 3) return PagingModel(
            params.page,
            params.size,
            listOf(
                WebAppInstance(
                    id = "123",
                    url = "voz.vn",
                    image = "fake",
                    "VOZ",
                )
            )
        )
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
                data = data
            )
        }
    }
}