package com.b1nd.alimo.presentation.feature.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.repository.ProfileRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
): BaseViewModel() {

    private val _sideEffect = Channel<ProfileSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()
    init {
        viewModelScope.launch {
            Log.d("TAG", ": start")
            val job1 = async {
                Log.d("TAG", ": hehe")
                repository.getInfo().catch {
//                    Log.d("TAG", ": ${it.message}")
                    _sideEffect.send(ProfileSideEffect.FailedLoad(it))
                }.collectLatest {
                    if (it is Resource.Error) {
                        Log.d("TAG", "${it.error?.message}: ")
                    } else if (it is Resource.Success) {
                        Log.d("TAG", ": ${it.data}")
                    }
                    Log.d("TAG", ": $it")
                    _state.value = _state.value.copy(
                        data = it.data?.data?.toModel()
                    )
                    Log.d("TAG", ": ${_state.value}")
                }
                Log.d("TAG", ": heheww")
            }

            val job2 = async {

            }
            job1.start()
            job2.start()
            job1.await()
            job2.await()
            _state.value = _state.value.copy(
                loading = false
            )
            Log.d("TAG", ": End")
        }
    }

    fun addCategory() {
        _state.value = _state.value.copy(
            isAdd = true
        )
    }

    fun onClickStudentCode() = viewEvent(ON_CLICK_STUDENT_CODE)

    fun onClickPrivatePolicy() = viewEvent(ON_CLICK_PRIVATE_POLICY)

    fun onClickServicePolicy() = viewEvent(ON_CLICK_SERVICE_POLICY)

    fun onClickLogout() = viewEvent(ON_CLICK_LOGOUT)

    companion object {
        const val ON_CLICK_STUDENT_CODE = "ON_CLICK_STUDENT_CODE"
        const val ON_CLICK_PRIVATE_POLICY = "ON_CLICK_PRIVATE_POLICY"
        const val ON_CLICK_SERVICE_POLICY = "ON_CLICK_SERVICE_POLICY"
        const val ON_CLICK_LOGOUT = "ON_CLICK_LOGOUT"
    }
}