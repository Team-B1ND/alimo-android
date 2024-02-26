package com.b1nd.alimo.data.remote.pagesource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.data.remote.service.HomeService

class HomePagingSource constructor(
    private val homeService: HomeService,
    private val category: String
): PagingSource<Int, NotificationModel>() {
    override fun getRefreshKey(state: PagingState<Int, NotificationModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NotificationModel> {
        val page = params.key ?: 1
        return try {
//            if (!Random.nextBoolean()) {
//                throw Exception("test Error")
//            }
            val response = homeService.getPost(
                page = page,
                size = params.loadSize,
                category = category,
            )
            when (response) {
                is Resource.Success -> {
                    LoadResult.Page(
                        data = response.data?: emptyList(),
                        prevKey = null,
                        nextKey = if (response.data.isNullOrEmpty()) null else page + 1
                    )
                }

                is Resource.Error -> {
                    LoadResult.Error(response.error?: Throwable())
                }
                is Resource.Loading -> {
                    LoadResult.Error(response.error?: Throwable())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}