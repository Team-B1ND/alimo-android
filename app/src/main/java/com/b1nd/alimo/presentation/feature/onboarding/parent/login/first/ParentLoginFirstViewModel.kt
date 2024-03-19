package com.b1nd.alimo.presentation.feature.onboarding.parent.login.first

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.request.ParentLoginRequest
import com.b1nd.alimo.data.repository.FirebaseTokenRepository
import com.b1nd.alimo.data.repository.ParentLoginRepository
import com.b1nd.alimo.data.repository.TokenRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.feature.onboarding.student.first.LoginModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParentLoginFirstViewModel @Inject constructor(
    private val parentLoginRepository: ParentLoginRepository,
    private val firebaseTokenRepository: FirebaseTokenRepository,
    private val tokenRepository: TokenRepository
): BaseViewModel() {

    private var _loginState = MutableSharedFlow<LoginModel>()
    val loginState: SharedFlow<LoginModel> = _loginState

    private var _fcmToken = MutableSharedFlow<String>(replay = 0)
    val fcmToken : SharedFlow<String> = _fcmToken

    // 학부모 로그인 기능
    fun login(email:String, password:String){
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TAG", "login: 시작2")
            firebaseTokenRepository.getToken().catch {
                Log.d("TAG", "login: $it")
            }.collectLatest {
                when(it){
                    is Resource.Success ->{
                        _fcmToken.emit(it.data?.fcmToken.toString())
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "에러 login: ${it.error}")
                    }
                    is Resource.Loading ->{
                        Log.d("TAG", "로딩 login: $it")
                    }
                }
            }

            Log.d("TAG", "login: 시작3")
            // fcmToken이 null이 아닐 때만 로그인 로직을 수행합니다.
            parentLoginRepository.login(
                ParentLoginRequest(
                    email = email,
                    password = password,
                    fcmToken = fcmToken.toString()
                )
            ).catch {
                Log.d("TAG", "login: ${it.message}")
            }.collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Log.d("TAG", "login: ${resource.data}")
                        val token = resource.data?.accessToken
                        val refreshToken = resource.data?.refreshToken
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