package com.b1nd.alimo.presentation.feature.main.home

sealed class HomeSideEffect {
    data class NotFound(val found: HomeFound): HomeSideEffect()
    data class NetworkError(val message: String): HomeSideEffect()
}

sealed class HomeFound {
    object Speaker: HomeFound()
    object Category: HomeFound()
    object Post: HomeFound()
}