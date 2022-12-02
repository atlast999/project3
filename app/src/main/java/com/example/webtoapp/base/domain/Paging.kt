package com.example.webtoapp.base.domain

object Paging {
    const val DEFAULT_STARTING_PAGE = 0
    const val DEFAULT_PAGE_SIZE = 20
}

open class PagingRequest(
    var page: Int = Paging.DEFAULT_STARTING_PAGE,
    var size: Int = Paging.DEFAULT_PAGE_SIZE
)

data class PagingModel<Model : Any>(
    val page: Int,
    val size: Int = Paging.DEFAULT_PAGE_SIZE,
    val data: List<Model>,
)