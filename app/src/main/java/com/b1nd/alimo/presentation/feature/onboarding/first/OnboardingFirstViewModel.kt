package com.b1nd.alimo.presentation.feature.onboarding.first

import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.TokenModel
import com.b1nd.alimo.data.repository.TokenRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.utiles.Dlog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingFirstViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
):BaseViewModel() {

    private val _tokenState = MutableStateFlow(TokenModel("", ""))
    val tokenState = _tokenState.asStateFlow()

    // 현재 토큰 Check
    fun tokenCheck(){
        viewModelScope.launch {
            tokenRepository.getToken().collect{
                when(it){
                    is Resource.Success ->{
                        Dlog.d("성공: ${it.data?.token} ${it.data?.refreshToken}")
                        _tokenState.value = _tokenState.value.copy(it.data?.token, it.data?.refreshToken)
                    }
                    is Resource.Error ->{
                        Dlog.e("중간 에러: ${it.error}")
                    }
                    is Resource.Loading ->{
                        Dlog.d("로딩 아래: $it")
                    }
                }
            }
        }

    }

}