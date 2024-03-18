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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

@HiltViewModel
class StudentLoginViewModel @Inject constructor(
    private val firebaseTokenRepository: FirebaseTokenRepository,
    private val dodamRepository: DodamRepository,
    private val studentLoginRepository: StudentLoinRepository,
    private val tokenRepository: TokenRepository
) : BaseViewModel() {

    private val _dodamCode = MutableSharedFlow<DodamState>()
    val dodamCode: SharedFlow<DodamState> = _dodamCode.asSharedFlow()

    private var _loginState = MutableSharedFlow<LoginModel>()
    val loginState: SharedFlow<LoginModel> = _loginState

    // 학생 로그인 기능
    fun login(code: String) {
        Log.d("TAG", "login: 시작")
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TAG", "login: 시작2")
            // FcmToken 저장
            var fcmToken = ""
            firebaseTokenRepository.getToken().catch {
                Log.d("TAG", "login: $it dxc")
            }.collect{
                when(it){
                    is Resource.Success ->{
                        fcmToken = it.data?.fcmToken.toString()
                    }
                    is Resource.Error ->{
                        Log.d("TAG", "login error:  ${it.error}")
                    }
                    is Resource.Loading ->{
                        Log.d("TAG", "login: $it")
                    }
                }
            }
            if (fcmToken != null) {
                Log.d("TAG", "login: 시작3")
                // FcmToken이 Null이 아닐 때만 로그인 로직을 수행
                studentLoginRepository.login(
                    StudentLoginRequest(
                        code = code,
                        fcmToken = fcmToken
                    )
                ).catch {
                    Log.d("TAG", "login: ${it.message}")
                }.collectLatest { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            // 성공하면 서버에서 받은 AccessToken과 RefreshToken 저장
                            Log.d("TAG", "login: ${resource.data?.data}")
                            val token = resource.data?.data?.accessToken
                            val refreshToken = resource.data?.data?.refreshToken
                            if (token != null && refreshToken != null) {
                                tokenRepository.insert(token, refreshToken)
                                _loginState.emit(
                                    LoginModel(
                                        accessToken = token,
                                        refreshToken = refreshToken
                                    )
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
            } else {
                Log.d("TAG", "login: $fcmToken")
            }
        }
    }

    fun tokenCheck() {
        viewModelScope.launch {
            val tokenEntity = tokenRepository.getToken()
            Log.d("TAG", "tokenCheck: $tokenEntity")
        }
    }

    // DAuth를 사용해서 Code를 가져오는 기능
    fun getCode(
        id: String,
        pw: String
    ) {
        viewModelScope.launch {
            dodamRepository.login(
                DodamRequest(
                    id = id,
                    pw = pw,
                    clientId = BuildConfig.CLIENT_ID,
                    redirectUrl = BuildConfig.REDIRECT_URL
                )
            ).catch {
                Log.d("TAG", "checkStudentCode: ${it.message}")
            }.collectLatest { resource ->
                when (resource) {
                    is Resource.Error -> {
                        Log.d("TAG", "실패: ${resource.error}")
                        _dodamCode.emit(DodamState(
                            error = "${resource.error}"
                        ))
                    }

                    is Resource.Success -> {
                        if (resource.data?.location != null) {
                            // 데이터에서 코드만 가져와서 저장
                            val code = resource.data.location.split("[=&]".toRegex())[1]
                            _dodamCode.emit(DodamState(code))
                            Log.d("TAG", "성공: ${code}")
                        }
                    }
                    is Resource.Loading -> {
                        Log.d("TAG", "로딩: ${resource.data}, ${resource.error}")
                    }
                }
            }
        }
    }

    // DAuth를 사용하기 위해 비번을 암호화
    fun sha512(text: String): String {
        val bytes = text.toByteArray()
        val md = MessageDigest.getInstance("SHA-512")
        val digest = md.digest(bytes)
        val pw = digest.fold("", { str, it -> str + "%02x".format(it) })
        Log.d("TAG", "sha512: $pw")
        return pw
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