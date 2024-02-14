package com.b1nd.alimo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.b1nd.alimo.data.remote.pagesource.HomePagingSource
import com.b1nd.alimo.data.remote.service.HomeService
import com.b1nd.alimo.presentation.feature.post.PostItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homePagingSource: HomePagingSource,
    private val homeService: HomeService
) {

    fun getPost(
    ): Flow<PagingData<PostItem>> =
        Pager(
            config = PagingConfig(pageSize = 15),
            pagingSourceFactory = { homePagingSource }
        ).flow


    suspend fun getCategory(

    ) = homeService.getCategory()
}