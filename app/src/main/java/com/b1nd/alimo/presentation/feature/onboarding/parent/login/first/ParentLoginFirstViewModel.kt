package com.b1nd.alimo.presentation.feature.onboarding.parent.login.first

import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.request.ParentLoginRequest
import com.b1nd.alimo.data.repository.FirebaseTokenRepository
import com.b1nd.alimo.data.repository.ParentLoginRepository
import com.b1nd.alimo.data.repository.TokenRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.feature.onboarding.student.first.LoginModel
import com.b1nd.alimo.presentation.utiles.Dlog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParentLoginFirstViewModel @Inject constructor(
    private val parentLoginRepository: ParentLoginRepository,
    private val firebaseTokenRepository: FirebaseTokenRepository,
    private val tokenRepository: TokenRepository
): BaseViewModel() {

    private var _loginState = MutableStateFlow(LoginModel())
    val loginState = _loginState.asStateFlow()

    private var _fcmToken = MutableStateFlow("")
    val fcmToken  = _fcmToken.asStateFlow()

    private var _parentLoginSideEffect = Channel<ParentLoginSideEffect>()
    val parentLoginSideEffect = _parentLoginSideEffect.receiveAsFlow()

    private val _isButtonClicked = MutableStateFlow<Boolean>(true)
    val isButtonClicked = _isButtonClicked.asStateFlow()


    // 학부모 로그인 기능
    fun login(email:String, password:String){
        viewModelScope.launch(Dispatchers.IO) {
            _isButtonClicked.value = false
            Dlog.d("login: 시작2")
            firebaseTokenRepository.getToken().collectLatest {
                when(it){
                    is Resource.Success ->{
                        _fcmToken.value = it.data?.fcmToken.toString()
                    }
                    is Resource.Error -> {
                        _parentLoginSideEffect.send(ParentLoginSideEffect.FailedLoadFcmToken(it.error ?:Throwable()))
                        Dlog.d("에러 login: ${it.error}")
                    }
                    is Resource.Loading ->{
                        Dlog.d("로딩 login: $it")
                    }
                }
            }

            Dlog.d("login: 시작3")
            // fcmToken이 null이 아닐 때만 로그인 로직을 수행합니다.
            parentLoginRepository.login(
                ParentLoginRequest(
                    email = email,
                    password = password,
                    fcmToken = fcmToken.value
                )
            ).collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Dlog.d("login: ${resource.data}")
                        val token = resource.data?.accessToken
                        val refreshToken = resource.data?.refreshToken
                        if (token != null && refreshToken != null) {
                            tokenRepository.insert(token, refreshToken)
                            _loginState.value = _loginState.value.copy(
                                accessToken = token,
                                refreshToken = refreshToken
                            )
                            delay(5000) // 2초 후 버튼 클릭 상태 초기화
                            _isButtonClicked.value = true
                        }
                    }

                    is Resource.Error -> {
                        _parentLoginSideEffect.send(ParentLoginSideEffect.FailedLogin(resource.error ?:Throwable()))
                        Dlog.d("login: 실패 ${resource.error}")
                    }

                    is Resource.Loading -> {
                        Dlog.d("login:로딩 ${resource.error} ${resource.data}")
                    }

                }
            }
        }
    }
    fun onClickBack() = viewEvent(ON_CLICK_BACK)
    fun onClickFindPW() = viewEvent(ON_CLICK_FIND_PW)
    fun onClickLogin() = viewEvent(ON_CLICK_LOGIN)
    fun onClickJoin() = viewEvent(ON_CLICK_JOIN)
    fun onClickBackground() = viewEvent(ON_CLICK_BACKGROUND)





    companion object{
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_FIND_PW = "ON_CLICK_FIND_PW"
        const val ON_CLICK_LOGIN = "ON_CLICK_LOGIN"
        const val ON_CLICK_JOIN = "ON_CLICK_JOIN"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
    }
}