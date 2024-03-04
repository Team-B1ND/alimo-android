package com.b1nd.alimo.presentation.feature.main.detail

sealed class DetailSideEffect {
    object FailedChangeEmoji: DetailSideEffect()
    data class FailedNotificationLoad(val throwable: Throwable): DetailSideEffect()
}