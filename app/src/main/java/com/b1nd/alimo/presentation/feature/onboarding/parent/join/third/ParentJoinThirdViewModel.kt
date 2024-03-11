package com.b1nd.alimo.presentation.feature.onboarding.parent.join.third

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.JoinModel
import com.b1nd.alimo.data.repository.ParentJoinRepository
import com.b1nd.alimo.data.repository.TokenRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParentJoinThirdViewModel @Inject constructor(
    private val parentJoinRepository: ParentJoinRepository,
    private val tokenRepository: TokenRepository
): BaseViewModel() {

    private var _parentJoinState = MutableSharedFlow<JoinModel>()
    val  parentJoinState: SharedFlow<JoinModel> = _parentJoinState

    fun emailCheck(
        email: String,
        code: String
    ){
        Log.d("TAG", "emailCheck: 시작")
        viewModelScope.launch {
            // 인증 코드를 서버로 전송
            parentJoinRepository.emailCheck(
                email = email,
                code = code
            ).catch {
                Log.d("TAG", "emailCheck: $it")
            }.collectLatest {resource ->
                when(resource){
                    is Resource.Success ->{
                        Log.d("TAG", "성공: ${resource.data}")
                        val token = resource.data?.data?.accessToken
                        val refreshToken = resource.data?.data?.refreshToken
                        // 성공시 토큰 저장
                        if (token != null && refreshToken != null) {
                            tokenRepository.insert(token, refreshToken)

                            _parentJoinState.emit(
                                JoinModel(
                                    accessToken = token,
                                    refreshToken = refreshToken
                                )
                            )
                        }else{
                            _parentJoinState.emit(
                                JoinModel(
                                    accessToken = null,
                                    refreshToken = null
                                )
                            )
                        }
                    }
                    is Resource.Error ->{
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
            parentJoinRepository.postEmailsVerification(email).catch {
                Log.d("TAG", "postEmail: $it")
            }.collectLatest {resource->
                when (resource){
                    is Resource.Success -> {
                        Log.d("TAG", "postEmail:성공 ${resource.data?.message}")
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "postEmail:실패 ${resource.error}")
                    }
                    is Resource.Loading -> {
                        Log.d("TAG", "로딩: ")
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