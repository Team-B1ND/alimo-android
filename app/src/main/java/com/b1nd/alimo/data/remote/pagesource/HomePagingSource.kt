package com.b1nd.alimo.data.remote.pagesource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.toTests
import com.b1nd.alimo.data.remote.service.HomeServices
import com.b1nd.alimo.presentation.feature.post.PostItem
import javax.inject.Inject

class HomePagingSource @Inject constructor(
    private val homeServices: HomeServices
): PagingSource<Int, PostItem>() {
    override fun getRefreshKey(state: PagingState<Int, PostItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostItem> {
        val page = params.key ?: 1
        return try {
//            if (!Random.nextBoolean()) {
//                throw Exception("test Error")
//            }
            val response = homeServices.getNotice(page = page)
            when (response) {
                is Resource.Success -> {
                    LoadResult.Page(
                        data = response.data!!.data.toTests(),
                        prevKey = null,
                        nextKey = if (response.data.data.isEmpty()) null else page + 1
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