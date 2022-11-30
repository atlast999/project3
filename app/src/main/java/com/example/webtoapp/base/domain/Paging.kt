package com.example.webtoapp.base.domain

object Paging {
    const val DEFAULT_STARTING_PAGE = 0
    const val DEFAULT_PAGE_SIZE = 20
}

open class PagingRequest(
    var page: Int = Paging.DEFAULT_STARTING_PAGE,
    var size: Int = Paging.DEFAULT_PAGE_SIZE
) {
    fun prevPage() = when (page) {
        Paging.DEFAULT_STARTING_PAGE -> null
        else -> PagingRequest(page.dec(), size)
    }

    fun nextPage() = PagingRequest(page.inc(), size)
}

data class PagingModel<Model : Any>(
    val page: Int,
    val size: Int = Paging.DEFAULT_PAGE_SIZE,
    val total: Int,
    val data: List<Model>,
)