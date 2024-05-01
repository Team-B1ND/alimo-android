package com.b1nd.alimo.presentation.feature.main.image

sealed class ImageSideEffect {
    data class FailedNotificationLoad(val throwable: Throwable): ImageSideEffect()
}