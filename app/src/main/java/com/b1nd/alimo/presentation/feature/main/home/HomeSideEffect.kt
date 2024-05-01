package com.b1nd.alimo.presentation.feature.main.home

sealed class HomeSideEffect {
    data class NotFound(val found: HomeFound): HomeSideEffect()
    data class NetworkError(val message: String): HomeSideEffect()
    data object FailedChangeEmoji: HomeSideEffect()
    data object FailedChangeBookmark: HomeSideEffect()
}

sealed class HomeFound {
    data object Speaker: HomeFound()
    data object Category: HomeFound()
    data object Post: HomeFound()
}