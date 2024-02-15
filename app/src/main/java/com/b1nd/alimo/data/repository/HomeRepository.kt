package com.b1nd.alimo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.data.remote.pagesource.HomePagingSource
import com.b1nd.alimo.data.remote.service.HomeService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homePagingSource: HomePagingSource,
    private val homeService: HomeService
) {

    fun getPost(
    ): Flow<PagingData<NotificationModel>> =
        Pager(
            config = PagingConfig(pageSize = 15),
            pagingSourceFactory = { homePagingSource }
        ).flow


    suspend fun getCategory(

    ) = homeService.getCategory()
}