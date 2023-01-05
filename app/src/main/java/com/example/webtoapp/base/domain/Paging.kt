package com.example.webtoapp.base.domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

object Paging {
    const val DEFAULT_STARTING_PAGE = 1
    const val DEFAULT_PAGE_SIZE = 20
}

open class PagingRequest(
    var page: Int = Paging.DEFAULT_STARTING_PAGE,
    var pageSize: Int = Paging.DEFAULT_PAGE_SIZE
) : IParamRequest {
    override fun buildQuery(): Map<String, String> {
        return buildMap {
            put("page", page.toString())
            put("size", pageSize.toString())
        }
    }
}

data class PagingModel<Model : Any>(
    @SerializedName("meta") @Expose val meta: PagingMeta,
    @SerializedName("items") @Expose val data: List<Model>,
)

data class PagingMeta(
    @SerializedName("page") @Expose val page: Int,
    @SerializedName("size") @Expose val size: Int
)

