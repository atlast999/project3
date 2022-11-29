package com.example.webtoapp.base.domain

object Paging {
    const val DEFAULT_PAGE_SIZE = 20
}

data class PagingModel<Model : Any>(
    val page: Int,
    val size: Int = Paging.DEFAULT_PAGE_SIZE,
    val total: Int,
    val data: List<Model>,
)