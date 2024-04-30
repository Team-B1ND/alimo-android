package com.b1nd.alimo.presentation.feature.onboarding.second

import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.repository.TokenRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.utiles.Dlog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingSecondViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
): BaseViewModel() {




    fun onClickStart() = viewEvent(ON_CLICK_START)



    fun tokenReset(){
        viewModelScope.launch {
            tokenRepository.insert("", "")

        }
    }



    companion object{
        const val ON_CLICK_START = "ON_CLICK_START"
    }
}