package com.b1nd.alimo.presentation.feature.main.bookmark

sealed class BookmarkSideEffect {
    data class NetworkError(val message: String): BookmarkSideEffect()
    data object FailedChangeEmoji: BookmarkSideEffect()
    data object FailedChangeBookmark: BookmarkSideEffect()
}
