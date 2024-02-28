package com.b1nd.alimo.presentation.feature.onboarding.first

import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.repository.TokenRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingFirstViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
):BaseViewModel() {

    var failed = false

    fun tokenCheck(): Boolean {
        viewModelScope.launch {
            val token = tokenRepository.getToken().token
            val refreshToken = tokenRepository.getToken().refreshToken
            if (token == null && refreshToken == null) {
                failed = true

            }
        }
        return failed
    }

}