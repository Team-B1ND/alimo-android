package com.b1nd.alimo.presentation.feature.onboarding.parent.join.first

sealed class ParentJoinFirstSideEffect {
    data class FailedChildCode(val throwable: Throwable): ParentJoinFirstSideEffect()

}