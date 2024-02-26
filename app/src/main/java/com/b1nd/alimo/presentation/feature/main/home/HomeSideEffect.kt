package com.b1nd.alimo.presentation.feature.main.home

sealed class HomeSideEffect {
    data class NotFound(val found: HomeFound): HomeSideEffect()
}

sealed class HomeFound {
    object Notice: HomeFound()
    object Category: HomeFound()
    object Post: HomeFound()
}