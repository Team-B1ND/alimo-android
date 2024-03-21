package com.b1nd.alimo.presentation.feature.onboarding.parent.login.first

sealed class ParentLoginSideEffect {
    data class FailedLoad(val throwable: Throwable): ParentLoginSideEffect()

    data class FailedLoadFcmToken(val throwable: Throwable): ParentLoginSideEffect()

    data class FailedLogin(val throwable: Throwable): ParentLoginSideEffect()

}