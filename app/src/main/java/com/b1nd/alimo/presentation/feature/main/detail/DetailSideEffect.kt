package com.b1nd.alimo.presentation.feature.main.detail

sealed class DetailSideEffect {
    data object SuccessChangeBookmark: DetailSideEffect()
    data object SuccessAddComment: DetailSideEffect()
    data object FailedChangeEmoji: DetailSideEffect()
    data class FailedChangeBookmark(val throwable: Throwable): DetailSideEffect()
    data class FailedNotificationLoad(val throwable: Throwable): DetailSideEffect()
    data class FailedEmojiLoad(val throwable: Throwable): DetailSideEffect()
    data class FailedPostComment(val throwable: Throwable): DetailSideEffect()
}