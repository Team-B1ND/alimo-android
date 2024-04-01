package com.b1nd.alimo.presentation.feature.onboarding.student.first

sealed class StudentLoginSideEffect {
    data class FailedLogin(val throwable: Throwable): StudentLoginSideEffect()
    data class FailedDAuth(val throwable: Throwable): StudentLoginSideEffect()
}