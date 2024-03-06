package com.b1nd.alimo.presentation.feature.onboarding.first

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.repository.TokenRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingFirstViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
):BaseViewModel() {

    private val _tokenState = MutableStateFlow(false)
    val tokenState = _tokenState.asStateFlow()

    fun tokenCheck(){
        viewModelScope.launch {
            kotlin.runCatching {
                tokenRepository.getToken()
            }.onSuccess {
                _tokenState.value = it.token != null
                Log.d("TAG", "tokenCheck: $it")
                Log.d("TAG", "tokenCheck: ${tokenState.value}")
            }.onFailure {
                Log.d("TAG", "tokenCheck: $it")
            }
        }

    }

}