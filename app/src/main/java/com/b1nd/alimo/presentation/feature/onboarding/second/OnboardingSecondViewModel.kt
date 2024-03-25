package com.b1nd.alimo.presentation.feature.onboarding.second

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.TokenModel
import com.b1nd.alimo.data.repository.TokenRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingSecondViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
): BaseViewModel() {


    private val _tokenState = MutableStateFlow(TokenModel("", ""))
    val tokenState = _tokenState.asStateFlow()

    // 현재 토큰 Check
    fun tokenCheck(){
        viewModelScope.launch {
            tokenRepository.getToken().catch {
                Log.d("TAG", "위에: $it")
            }.collect{
                when(it){
                    is Resource.Success ->{
                        Log.d("TAG", "성공: ${it.data?.token} ${it.data?.refreshToken}")
                        _tokenState.value = _tokenState.value.copy(
                            token = it.data?.token,
                            refreshToken = it.data?.refreshToken
                        )
                    }
                    is Resource.Error ->{
                        Log.d("TAG", "중간 에러: ${it.error}")
                    }
                    is Resource.Loading ->{
                        Log.d("TAG", "로딩 아래: $it")
                    }
                }
            }
        }

    }

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