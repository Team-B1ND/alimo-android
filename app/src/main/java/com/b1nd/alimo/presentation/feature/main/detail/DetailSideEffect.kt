package com.b1nd.alimo.presentation.feature.main.detail

sealed class DetailSideEffect {
    object SuccessChangeBookmark: DetailSideEffect()
    object FailedChangeEmoji: DetailSideEffect()
    data class FailedChangeBookmark(val throwable: Throwable): DetailSideEffect()
    data class FailedNotificationLoad(val throwable: Throwable): DetailSideEffect()
    data class FailedEmojiLoad(val throwable: Throwable): DetailSideEffect()
}