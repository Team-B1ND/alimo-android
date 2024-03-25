package com.b1nd.alimo.presentation.feature.onboarding.parent.join.second

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.remote.request.ParentJoinRequest
import com.b1nd.alimo.data.repository.FirebaseTokenRepository
import com.b1nd.alimo.data.repository.ParentJoinRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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

    private val _parentJoinSideEffect = Channel<ParentJoinSecondSideEffect>()
    val parentJoinSecondSideEffect = _parentJoinSideEffect.receiveAsFlow()

    // 학생 이름 가져오는 기능
    init {
        viewModelScope.launch {
            studentCode.collectLatest { code ->
                parentJoinRepository.member(code).catch { exception ->
                    _parentJoinSideEffect.send(ParentJoinSecondSideEffect.FailedLoad(exception))
                    Log.d("TAG", "getMemberName: ${exception.message}")
                }.collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            Log.d("TAG", ":서공  ${resource.data?.name}")
                            _memberName.emit(MemberNameModel(resource.data?.name))
                        }

                        is Resource.Error -> {
                            _parentJoinSideEffect.send(ParentJoinSecondSideEffect.FailedMemberName(resource.error ?: Throwable()))
                            Log.d("TAG", ":실패  ${resource.error}")
                        }

                        is Resource.Loading -> {
                            Log.d("TAG", ": 로딩")
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
            Log.d("TAG", "singUp: ${firebaseTokenRepository.getToken()}")
            firebaseTokenRepository.getToken().catch { firebaseException ->
                _parentJoinSideEffect.send(ParentJoinSecondSideEffect.FailedLoad(firebaseException))
            }.collect { firebaseResource ->
                when (firebaseResource) {
                    is Resource.Success -> {
                        val fcmToken = firebaseResource.data?.fcmToken
                        if (fcmToken != null) {

                            Log.d(
                                "TAG",
                                "$email $password ${fcmToken} $childCode $memberId"
                            )
                            parentJoinRepository.singUp(
                                data = ParentJoinRequest(
                                    email = email,
                                    password = password,
                                    fcmToken = fcmToken,
                                    childCode = childCode,
                                    memberId = memberId
                                )
                            ).catch { exception ->
                                _parentJoinSideEffect.send(ParentJoinSecondSideEffect.FailedLoad(exception))
                                Log.d("TAG", "singUp: ${exception.message}")
                            }.collect { resource ->
                                when (resource) {
                                    is Resource.Success -> {
                                        val status = resource.data?.status
                                        if (status == 200){
                                            _parentJoinSideEffect.send(ParentJoinSecondSideEffect.SuccessSignup)

                                        }else{
                                            _parentJoinSideEffect.send(ParentJoinSecondSideEffect.FailedSignup(resource.error ?:Throwable()))
                                        }
                                        Log.d("TAG", "singUp: 성공 ${resource.data?.status}")
                                    }

                                    is Resource.Error -> {
                                        _parentJoinSideEffect.send(ParentJoinSecondSideEffect.FailedSignup(resource.error ?: Throwable()))
                                        Log.d("TAG", "singUp: 에러 ${resource.error}, ${resource.data}"
                                        )
                                    }

                                    is Resource.Loading -> {
                                        Log.d("TAG", "singUp: 로딩")
                                    }

                                }
                            }
                        }

                    }

                    is Resource.Error -> {
                        Log.d("TAG", "singUp: 에서 ${firebaseResource.error}")
                    }

                    is Resource.Loading -> {
                        Log.d("TAG", "singUp: $firebaseResource")
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
    fun failure() = viewEvent(FAILURE)


    companion object {
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_NEXT = "ON_CLICK_NEXT"
        const val ON_CLICK_LOGIN = "ON_CLICK_LOGIN"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
        const val SUCCESS = "SUCCESS"
        const val FAILURE = "FAILURE"
    }
}