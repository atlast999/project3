package com.example.webtoapp.base.adapter

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.webtoapp.base.domain.Paging
import com.example.webtoapp.base.domain.PagingModel
import com.example.webtoapp.base.domain.PagingRequest
import com.example.webtoapp.base.util.collectLatestInScope
import com.example.webtoapp.base.viewmodel.BaseViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@OptIn(FlowPreview::class)
fun <Result : Any> BaseViewModel.pagingFlow(
    request: PagingRequest,
    fetchBy: suspend (PagingRequest) -> PagingModel<Result>,
    invalidationFlows: List<Flow<*>>,
): Flow<PagingData<Result>> {
    val pagingSourceFlow = MutableStateFlow<PagingSource<*, *>?>(null)
    flowOf(*invalidationFlows.toTypedArray()).flattenMerge().collectLatestInScope(viewModelScope) {
        pagingSourceFlow.value?.invalidate()
    }
    return Pager(
        config = PagingConfig(
            pageSize = request.pageSize,
            enablePlaceholders = false,
            maxSize = request.pageSize * 5,
        ),
        pagingSourceFactory = {
            object : PagingSource<Int, Result>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
                    val loadingPage = params.key ?: Paging.DEFAULT_STARTING_PAGE
                    return try {
                        val data = fetchBy.invoke(request.apply {
                            page = loadingPage
                        }).data
                        val previousKey =
                            if (loadingPage == Paging.DEFAULT_STARTING_PAGE) null else loadingPage.dec()
                        val nextKey = if (data.size < request.pageSize) null else loadingPage.inc()
                        LoadResult.Page(
                            data = data,
                            prevKey = previousKey,
                            nextKey = nextKey,
                        )
                    } catch (e: Exception) {
                        LoadResult.Error(e)
                    }
                }

                override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
                    val position = state.anchorPosition ?: return null
                    return state.closestPageToPosition(position)?.let {
                        it.nextKey?.dec()
                    }
                }
            }.also {
                pagingSourceFlow.value = it
            }
        }
    ).flow.cachedIn(viewModelScope)
}
