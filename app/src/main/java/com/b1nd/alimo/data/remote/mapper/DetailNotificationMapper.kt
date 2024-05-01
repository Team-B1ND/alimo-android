package com.b1nd.alimo.data.remote.mapper

import com.b1nd.alimo.data.model.CommentModel
import com.b1nd.alimo.data.model.DetailNotificationModel
import com.b1nd.alimo.data.model.SubCommentModel
import com.b1nd.alimo.data.remote.response.detail.CommentResponse
import com.b1nd.alimo.data.remote.response.detail.DetailNotificationResponse
import com.b1nd.alimo.data.remote.response.detail.SubCommentResponse

internal fun DetailNotificationResponse.toModel() =
    DetailNotificationModel(
        notificationId = notificationId,
        title = title,
        memberId = memberId,
        member = name,
        memberProfile = profileImage,
        createdAt = createdAt,
        content = content,
        speaker = speaker,
        image = image,
        isBookmark = isBookmark,
        isNew = false,
        emoji = emoji,
        images = images.toModels(),
        files = files.toModels(),
        comments = comments.toModels()
    )


@JvmName("DetailNotificationResponseToModels")
internal fun List<DetailNotificationResponse>.toModels() =
    this.map {
        it.toModel()
    }


internal fun CommentResponse.toModel() =
    CommentModel(
        commentId = commentId,
        content = content,
        commenter = commenter,
        createdAt = createdAt,
        profileImage = profileImage,
        subComments = subComments.toModels()
    )

@JvmName("CommentResponseToModels")
internal fun List<CommentResponse>.toModels() =
    this.map {
        it.toModel()
    }

internal fun SubCommentResponse.toModel() =
    SubCommentModel(
        commentId = commentId,
        content = content,
        commenter = commenter,
        createdAt = createdAt,
        profileImage = profileImage
    )

@JvmName("SubCommentResponseToModels")
internal fun List<SubCommentResponse>.toModels() =
    this.map {
        it.toModel()
    }
