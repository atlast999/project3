package com.example.webtoapp.base.adapter

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.webtoapp.base.domain.Paging
import com.example.webtoapp.base.domain.PagingModel
import com.example.webtoapp.base.viewmodel.BaseViewModel

fun <Param : Any, Result : Any> BaseViewModel.pagingFlow(
    fetchBy: suspend (Param) -> PagingModel<Result>,
    params: Param,
) = Pager(
    config = PagingConfig(Paging.DEFAULT_PAGE_SIZE),
    initialKey = params,
    pagingSourceFactory = {
        object : PagingSource<Param, Result>() {
            override suspend fun load(params: LoadParams<Param>): LoadResult<Param, Result> {
                return params.key?.let { request ->
                    LoadResult.Page(
                        data = fetchBy.invoke(request).data,
                        prevKey = request,
                        nextKey = null,
                    )
                } ?: LoadResult.Invalid()
            }

            override fun getRefreshKey(state: PagingState<Param, Result>): Param {
                return params
            }
        }
    }
).flow.cachedIn(viewModelScope)
