package com.b1nd.alimo.presentation.feature.onboarding.parent.join.third

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.JoinModel
import com.b1nd.alimo.data.repository.ParentJoinRepository
import com.b1nd.alimo.data.repository.TokenRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.utiles.Dlog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParentJoinThirdViewModel @Inject constructor(
    private val parentJoinRepository: ParentJoinRepository,
    private val tokenRepository: TokenRepository
): BaseViewModel() {

    private var _parentJoinState = MutableStateFlow(JoinModel())
    val  parentJoinState =_parentJoinState.asStateFlow()

    private val _parentJoinThirdSideEffect = Channel<ParentJoinThirdSideEffect>()
    val parentJoinThirdSideEffect = _parentJoinThirdSideEffect.receiveAsFlow()

    fun emailCheck(
        email: String,
        code: String
    ){
        Dlog.d("emailCheck: 시작")
        viewModelScope.launch {
            // 인증 코드를 서버로 전송
            parentJoinRepository.emailCheck(
                email = email,
                code = code
            ).collectLatest {resource ->
                when(resource){
                    is Resource.Success ->{
                        _parentJoinThirdSideEffect.send(ParentJoinThirdSideEffect.Success)
                        Log.d("TAG", "성공: ${resource.data}")
                        val token = resource.data?.accessToken
                        val refreshToken = resource.data?.refreshToken
                        // 성공시 토큰 저장
                        if (token != null && refreshToken != null) {
                            tokenRepository.insert(token, refreshToken)

                            _parentJoinState.value = _parentJoinState.value.copy(
                                accessToken = token,
                                refreshToken = refreshToken
                            )
                        }
                    }
                    is Resource.Error ->{
                        _parentJoinThirdSideEffect.send(ParentJoinThirdSideEffect.FailedEmailCheck(resource.error ?: Throwable()))
                        Log.d("TAG", "실패: ${resource.error}")
                    }
                    is Resource.Loading ->{
                        Log.d("TAG", "로딩: ")
                    }
                }
            }
        }
    }

    // 인증 요청을 서버에 요청
    fun postEmail(
        email: String
    ){
        viewModelScope.launch {
            parentJoinRepository.postEmailsVerification(email).collectLatest {resource->
                when (resource){
                    is Resource.Success -> {
                        Dlog.d("postEmail:성공 ${resource.data?.message}")
                    }
                    is Resource.Error -> {
                        _parentJoinThirdSideEffect.send(ParentJoinThirdSideEffect.FailedPostEmail(resource.error ?: Throwable()))
                        Log.d("TAG", "postEmail:실패 ${resource.error}")
                    }
                    is Resource.Loading -> {
                        Dlog.d("로딩: ")
                    }
                }
            }
        }
    }


    fun onClickBack() = viewEvent(ON_CLICK_BACK)
    fun onClickBackground() = viewEvent(ON_CLICK_BACKGROUND)
    fun onClickJoin() = viewEvent(ON_CLICK_JOIN)
    fun onClickCertification() = viewEvent(ON_CLICK_CERTIFICATION)
    fun onClickCheck() = viewEvent(ON_CLICK_CHECK)

    companion object{
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
        const val ON_CLICK_JOIN = "ON_CLICK_JOIN"
        const val ON_CLICK_CERTIFICATION = "ON_CLICK_CERTIFICATION"
        const val ON_CLICK_CHECK = "ON_CLICK_CHECK"

    }
}