package com.b1nd.alimo.data.remote.pagesource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.data.remote.mapper.toModels
import com.b1nd.alimo.data.remote.service.PostService

class BookmarkPagingSource constructor(
    private val postService: PostService
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
            val response = postService.getBookmark(
                page = page,
                size = params.loadSize,
            ).data.toModels()

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}