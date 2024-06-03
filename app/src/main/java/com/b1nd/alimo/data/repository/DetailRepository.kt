package com.b1nd.alimo.data.repository

import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.DetailNotificationModel
import com.b1nd.alimo.data.model.EmojiModel
import com.b1nd.alimo.data.remote.mapper.toModel
import com.b1nd.alimo.data.remote.mapper.toModels
import com.b1nd.alimo.data.remote.safeFlow
import com.b1nd.alimo.data.remote.service.DetailService
import javax.inject.Inject


class DetailRepository @Inject constructor(
    private val detailService: DetailService
) {

    suspend fun patchEmojiEdit(
        notificationId: Int,
        reaction: String
    ) = safeFlow<String?> {
        val response = detailService.patchEmojiEdit(
            notificationId = notificationId,
            reaction = reaction
        ).errorCheck()
        emit(Resource.Success(null))
    }

    suspend fun loadNotification(
        notificationId: Int
    ) = safeFlow<DetailNotificationModel> {
        val response = detailService.loadNotification(
            notificationId = notificationId
        ).errorCheck()
        emit(
            Resource.Success(
                data = response.data.toModel()
            )
        )
    }

    suspend fun pathBookmark(notificationId: Int) = safeFlow<String?> {
        val response = detailService.pathBookmark(
            notificationId = notificationId
        ).errorCheck()

        emit(Resource.Success(null))
    }

    suspend fun loadEmoji(notificationId: Int) = safeFlow<List<EmojiModel>> {
        val response = detailService.loadEmoji(
            notificationId = notificationId
        ).errorCheck()

        emit(
            Resource.Success(
                data = response.data.toModels()
            )
        )
    }

    suspend fun postComment(
        notificationId: Int,
        text: String,
        commentId: Int?,
    ) = safeFlow<String?> {
        val response = detailService.postComment(
            notificationId = notificationId,
            text = text,
            commentId = commentId
        ).errorCheck()

        emit(Resource.Success(null))
    }

    suspend fun deleteComment(
        commentId: Int
    ) = safeFlow<Unit> {
        detailService.deleteComment(
            commentId = commentId
        ).errorCheck()

        emit(Resource.Success(Unit))
    }

    suspend fun deleteSubComment(
        commentId: Int
    ) = safeFlow<Unit> {
        detailService.deleteSubComment(
            commentId = commentId
        ).errorCheck()

        emit(Resource.Success(Unit))
    }


}