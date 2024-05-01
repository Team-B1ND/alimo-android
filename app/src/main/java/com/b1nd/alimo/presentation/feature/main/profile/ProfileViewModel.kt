package com.b1nd.alimo.presentation.feature.main.profile

import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.repository.AlarmRepository
import com.b1nd.alimo.data.repository.ProfileRepository
import com.b1nd.alimo.data.repository.TokenRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.utiles.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val alarmRepository: AlarmRepository,
    private val tokenRepository: TokenRepository
): BaseViewModel() {

    // 현재 알람 상태
    private val _alarmState =  MutableStateFlow(false)
    val alarmState = _alarmState.asStateFlow()

    private val _sideEffect = Channel<ProfileSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()


    // 유저 정보 불러오기
    fun loadProfile() = viewModelScope.launch(Dispatchers.IO) {
        val job1 = async {
            repository.getInfo().catch {
                _sideEffect.send(ProfileSideEffect.FailedLoad(it))
            }.collectLatest {
                if (it is Resource.Error) {
                    _sideEffect.send(ProfileSideEffect.FailedLoadInfo(it.error?: Throwable()))
                }
                _state.value = _state.value.copy(
                    data = it.data,
                    isAdd = false
                )
            }
        }

        val job2 = async {
            repository.getCategory().catch {
                _sideEffect.send(ProfileSideEffect.FailedLoad(it))
            }.collectLatest {
                if (it is Resource.Error) {
                    _sideEffect.send(ProfileSideEffect.FailedLoadCategory(it.error?: Throwable()))
                }

                _state.value = _state.value.copy(
                    category = it.data?.roles ?: emptyList()
                )
            }
        }
        job1.start()
        job2.start()
        job1.await()
        job2.await()
        _state.value = _state.value.copy(
            loading = false
        )
    }

    // 카테고리가 추가되었음을 알리는 함수
    fun addCategory() {
        _state.value = _state.value.copy(
            isAdd = true
        )
    }

    // 회원탈퇴
    fun withdrawal() = launchIO {
        repository.deleteWithdrawal().collectLatest {
            when(it) {
                is Resource.Success -> {
                    _sideEffect.send(ProfileSideEffect.SuccessWithdrawal)
                }
                is Resource.Loading -> {}
                is Resource.Error -> {
                    _sideEffect.send(ProfileSideEffect.FailedWithdrawal(it.error?: Throwable()))
                }
            }
        }
        repository.deleteToken()
    }

    // 현재 알림 가져오기
    fun loadAlarm(){
        viewModelScope.launch(Dispatchers.IO) {
            _alarmState.value = alarmRepository.getAlarmState()
        }
    }

    // 서버에게 현재 알림 상태 보내기
    fun setAlarmState(value: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            repository.setAlarmState(value).collect()
            alarmRepository.setAlarmState(value)
        }
    }

    // 로그아웃
    fun logout(){
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                tokenRepository.deleteToken()
                repository.deleteToken()
            }.onSuccess {
                _sideEffect.send(ProfileSideEffect.SuccessLogout)
            }
        }
    }
    fun onClickStudentCode() = viewEvent(ON_CLICK_STUDENT_CODE)

    fun onClickPrivatePolicy() = viewEvent(ON_CLICK_PRIVATE_POLICY)

    fun onClickServicePolicy() = viewEvent(ON_CLICK_SERVICE_POLICY)

    fun onClickLogout() = viewEvent(ON_CLICK_LOGOUT)

    fun onClickWithdrawal() = viewEvent(ON_CLICK_WITHDRAWAL)

    companion object {
        const val ON_CLICK_STUDENT_CODE = "ON_CLICK_STUDENT_CODE"
        const val ON_CLICK_PRIVATE_POLICY = "ON_CLICK_PRIVATE_POLICY"
        const val ON_CLICK_SERVICE_POLICY = "ON_CLICK_SERVICE_POLICY"
        const val ON_CLICK_LOGOUT = "ON_CLICK_LOGOUT"
        const val ON_CLICK_WITHDRAWAL = "ON_CLICK_WITHDRAWAL"
    }
}