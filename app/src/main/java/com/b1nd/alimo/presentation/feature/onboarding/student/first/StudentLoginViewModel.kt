package com.b1nd.alimo.presentation.feature.onboarding.student.first

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.BuildConfig
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.request.DodamRequest
import com.b1nd.alimo.data.remote.request.StudentLoginRequest
import com.b1nd.alimo.data.repository.DodamRepository
import com.b1nd.alimo.data.repository.FirebaseTokenRepository
import com.b1nd.alimo.data.repository.StudentLoinRepository
import com.b1nd.alimo.data.repository.TokenRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.utiles.Dlog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentLoginViewModel @Inject constructor(
    private val firebaseTokenRepository: FirebaseTokenRepository,
    private val dodamRepository: DodamRepository,
    private val studentLoginRepository: StudentLoinRepository,
    private val tokenRepository: TokenRepository
) : BaseViewModel() {

    private val _dodamCode = MutableStateFlow(DodamState())
    val dodamCode = _dodamCode.asStateFlow()

    private var _loginState = MutableStateFlow(LoginModel())
    val loginState = _loginState.asStateFlow()

    private var _fcmToken = MutableStateFlow("")
    val fcmToken = _fcmToken.asStateFlow()


    private var _studentLoginSideEffect = Channel<StudentLoginSideEffect>()
    val studentLoginSideEffect = _studentLoginSideEffect.receiveAsFlow()

    private val _isButtonClicked = MutableStateFlow<Boolean>(false)
    val isButtonClicked = _isButtonClicked.asStateFlow()

    // 학생 로그인 기능
    fun login(code: String) {
        Dlog.d("login: 시작")
        viewModelScope.launch(Dispatchers.IO) {
            Dlog.d("login: 시작2")
            // FcmToken 저장

            firebaseTokenRepository.getToken().collect {
                when (it) {
                    is Resource.Success -> {
                        _fcmToken.value = it.data?.fcmToken.toString()
                    }

                    is Resource.Error -> {
                        _studentLoginSideEffect.send(
                            StudentLoginSideEffect.FailedLogin(
                                it.error ?: Throwable()
                            )
                        )
                        Log.d("TAG", "login error:  ${it.error}")
                    }

                    is Resource.Loading -> {
                        Log.d("TAG", "login: $it")
                    }
                }
            }
            Log.d("TAG", "login: 시작3")
            // FcmToken이 Null이 아닐 때만 로그인 로직을 수행
            studentLoginRepository.login(
                StudentLoginRequest(
                    code = code,
                    fcmToken = fcmToken.value
                )
            ).collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        // 성공하면 서버에서 받은 AccessToken과 RefreshToken 저장
                        Log.d("TAG", "login: ${resource.data}")
                        val token = resource.data?.accessToken
                        val refreshToken = resource.data?.refreshToken
                        if (token != null && refreshToken != null) {
                            tokenRepository.insert(token, refreshToken)
                            _loginState.value = _loginState.value.copy(
                                accessToken = token,
                                refreshToken = refreshToken
                            )
                        }
                    }

                    is Resource.Error -> {
                        Log.d("TAG", "login: 실패 ${resource.error}")
                    }

                    is Resource.Loading -> {
                        Log.d("TAG", "login:로딩 ${resource.error} ${resource.data}")
                    }

                }
            }
        }
    }



    // DAuth를 사용해서 Code를 가져오는 기능
    fun getCode(
        id: String,
        pw: String
    ) {
        viewModelScope.launch {
            _isButtonClicked.value = true
            dodamRepository.login(
                DodamRequest(
                    id = id,
                    pw = pw,
                    clientId = BuildConfig.CLIENT_ID,
                    redirectUrl = BuildConfig.REDIRECT_URL
                )
            ).collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _studentLoginSideEffect.send(
                            StudentLoginSideEffect.FailedDAuth(
                                resource.error ?: Throwable()
                            )
                        )
                        _isButtonClicked.value = false
                        Log.d("TAG", "실패: ${resource.error}")
                    }

                    is Resource.Success -> {
                        if (resource.data?.location != null) {
                            // 데이터에서 코드만 가져와서 저장
                            val code = resource.data.location.split("[=&]".toRegex())[1]
                            _dodamCode.value = _dodamCode.value.copy(code = code)
                            _isButtonClicked.value = false
                            Log.d("TAG", "성공: ${code}")
                        }
                    }

                    is Resource.Loading -> {
                        Dlog.d("로딩: ${resource.data}, ${resource.error}")
                    }
                }
            }
        }
    }


    fun onClickBack() = viewEvent(ON_CLICK_BACK)
    fun onClickLoginOn() = viewEvent(ON_CLICK_LOGIN_ON)
    fun onClickBackground() = viewEvent(ON_CLICK_BACKGROUND)
    fun onClickLoginOff() = viewEvent(ON_CLICK_LOGIN_OFF)

    companion object {
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_LOGIN_ON = "ON_CLICK_LOGIN_ON"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
        const val ON_CLICK_LOGIN_OFF = "ON_CLICK_LOGIN_OFF"
    }
}