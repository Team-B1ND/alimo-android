package com.b1nd.alimo.presentation.feature.onboarding.parent.join.second

import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.request.ParentJoinRequest
import com.b1nd.alimo.data.repository.FirebaseTokenRepository
import com.b1nd.alimo.data.repository.ParentJoinRepository
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
class ParentJoinSecondViewModel @Inject constructor(
    private val parentJoinRepository: ParentJoinRepository,
    private val firebaseTokenRepository: FirebaseTokenRepository
) : BaseViewModel() {

    private val _studentCode = MutableStateFlow("")
    private val studentCode = _studentCode.asStateFlow()

    private val _memberName = MutableStateFlow(MemberNameModel())
    val memberName = _memberName.asStateFlow()

    private var _fcmToken = MutableStateFlow("")
    val fcmToken = _fcmToken.asStateFlow()


    private val _parentJoinSideEffect = Channel<ParentJoinSecondSideEffect>()
    val parentJoinSecondSideEffect = _parentJoinSideEffect.receiveAsFlow()

    private val _isButtonClicked = MutableStateFlow<Boolean>(true)
    val isButtonClicked = _isButtonClicked.asStateFlow()

    // 학생 이름 가져오는 기능
    init {
        viewModelScope.launch {
            studentCode.collectLatest { code ->
                parentJoinRepository.member(code).collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            Dlog.d(":서공  ${resource.data?.name}")
                            _memberName.value = _memberName.value.copy(resource.data?.name)
                        }

                        is Resource.Error -> {
                            _parentJoinSideEffect.send(ParentJoinSecondSideEffect.FailedMemberName(resource.error ?: Throwable()))
                            Dlog.e(":실패  ${resource.error}")
                        }

                        is Resource.Loading -> {
                            Dlog.d(": 로딩")
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    fun setStudentCode(code: String) {
        _studentCode.value = code
    }

    // 학부모 회원가입
    fun singUp(
        email: String,
        password: String,
        childCode: String,
        memberId: Int
    ) {
        viewModelScope.launch {
            _isButtonClicked.value = false
            Dlog.d("singUp: ${firebaseTokenRepository.getToken()}")
            firebaseTokenRepository.getToken().collect { firebaseResource ->
                when (firebaseResource) {
                    is Resource.Success -> {
                        _fcmToken.value = firebaseResource.data?.fcmToken.toString()

                            Dlog.d("$email $password ${fcmToken} $childCode $memberId")
                            parentJoinRepository.singUp(
                                data = ParentJoinRequest(
                                    email = email,
                                    password = password,
                                    fcmToken = fcmToken.value,
                                    childCode = childCode,
                                    memberId = memberId
                                )
                            ).collect { resource ->
                                when (resource) {
                                    is Resource.Success -> {
                                        val status = resource.data?.status
                                        if (status == 200){
                                            _parentJoinSideEffect.send(ParentJoinSecondSideEffect.SuccessSignup)
                                            _isButtonClicked.value = true
                                        }else{
                                            _parentJoinSideEffect.send(ParentJoinSecondSideEffect.FailedSignup(resource.error ?:Throwable()))
                                        }
                                        Dlog.d("singUp: 성공 ${resource.data?.status}")
                                    }

                                    is Resource.Error -> {
                                        _parentJoinSideEffect.send(ParentJoinSecondSideEffect.FailedSignup(resource.error ?: Throwable()))
                                        _isButtonClicked.value = true
                                        Dlog.e("singUp: 에러 ${resource.error}, ${resource.data}"
                                        )
                                    }

                                    is Resource.Loading -> {
                                        Dlog.d("singUp: 로딩")
                                    }

                                }
                            }


                    }

                    is Resource.Error -> {
                        Dlog.d("singUp: 에서 ${firebaseResource.error}")
                    }

                    is Resource.Loading -> {
                        Dlog.d("singUp: $firebaseResource")
                    }
                }
            }


        }

    }

    fun onClickBack() = viewEvent(ON_CLICK_BACK)
    fun onClickNext() = viewEvent(ON_CLICK_NEXT)
    fun onClickLogin() = viewEvent(ON_CLICK_LOGIN)
    fun onClickBackground() = viewEvent(ON_CLICK_BACKGROUND)

    fun success() = viewEvent(SUCCESS)


    companion object {
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_NEXT = "ON_CLICK_NEXT"
        const val ON_CLICK_LOGIN = "ON_CLICK_LOGIN"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
        const val SUCCESS = "SUCCESS"
    }
}