package com.example.webtoapp.base.adapter

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.webtoapp.base.domain.PagingModel
import com.example.webtoapp.base.domain.PagingRequest
import com.example.webtoapp.base.viewmodel.BaseViewModel

fun <Result : Any> BaseViewModel.pagingFlow(
    params: PagingRequest,
    fetchBy: suspend (PagingRequest) -> PagingModel<Result>,
) = Pager(
    config = PagingConfig(
        pageSize = params.size,
//        maxSize = params.size * 3,
    ),
    pagingSourceFactory = {
        object : PagingSource<PagingRequest, Result>() {
            override suspend fun load(params: LoadParams<PagingRequest>): LoadResult<PagingRequest, Result> {
                return params.key?.let { request ->
                    LoadResult.Page(
                        data = fetchBy.invoke(request).data,
                        prevKey = request.prevPage(),
                        nextKey = request.nextPage(),
                    )
                } ?: LoadResult.Invalid()
            }

            override fun getRefreshKey(state: PagingState<PagingRequest, Result>): PagingRequest? {
                val position = state.anchorPosition ?: return params
                return state.closestPageToPosition(position)?.let {
                    it.nextKey?.apply {
                        prevPage()
                    }
                }
            }
        }
    }
).flow.cachedIn(viewModelScope)
