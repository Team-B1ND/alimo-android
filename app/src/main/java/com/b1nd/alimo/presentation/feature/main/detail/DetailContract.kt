package com.b1nd.alimo.presentation.feature.main.detail

import com.b1nd.alimo.data.model.DetailNotificationModel

data class DetailState(
    val isLoading: Boolean = true,
    val notificationState: DetailNotificationModel? = null
)

sealed class DetailSideEffect {
    data object SuccessChangeBookmark: DetailSideEffect()
    data object SuccessAddComment: DetailSideEffect()
    data object SuccessDeleteComment: DetailSideEffect()
    data object FailedChangeEmoji: DetailSideEffect()
    data class FailedChangeBookmark(val throwable: Throwable): DetailSideEffect()
    data class FailedNotificationLoad(val throwable: Throwable): DetailSideEffect()
    data class FailedEmojiLoad(val throwable: Throwable): DetailSideEffect()
    data class FailedPostComment(val throwable: Throwable): DetailSideEffect()
    data class FailedLoadProfile(val throwable: Throwable): DetailSideEffect()
    data class FailedDeleteComment(val throwable: Throwable): DetailSideEffect()
}