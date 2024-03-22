package com.b1nd.alimo.presentation.feature.onboarding.parent.join.first

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.repository.ParentJoinRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParentJoinFirstViewModel @Inject constructor(
    private val parentJoinRepository: ParentJoinRepository
): BaseViewModel() {

    private val _trueFalse = MutableSharedFlow<ParentJoinFirstModel>(replay = 0)
    val trueFalse: SharedFlow<ParentJoinFirstModel> = _trueFalse.asSharedFlow()

    private val _parentJoinSideEffect = Channel<ParentJoinFirstSideEffect>()
    val parentJoinFirstSideEffect = _parentJoinSideEffect.receiveAsFlow()

    // 학생 코드 인증
    fun checkStudentCode(studentCode: String){
        viewModelScope.launch {
            parentJoinRepository.childCode(studentCode).catch {
                _parentJoinSideEffect.send(ParentJoinFirstSideEffect.FailedLoad(it))
                Log.d("TAG", "checkStudentCode: ${it.message}")
            }.collectLatest {resource ->
                when(resource){
                    is Resource.Error ->{
                        _parentJoinSideEffect.send(ParentJoinFirstSideEffect.FailedChildCode(resource.error ?: Throwable()))
                        Log.d("TAG", "실패: ")
                    }
                    is Resource.Success ->{
                        val newEffect = ParentJoinFirstModel(resource.data?.isCorrectChildCode, resource.data?.memberId)
                        _trueFalse.emit(newEffect)
                        Log.d("TAG", "성공: ${resource.data}")
                    }
                    is Resource.Loading ->{
                        Log.d("TAG", "로딩: ")
                    }
                }
            }
        }
    }



    fun onClickBack() = viewEvent(ON_CLICK_BACK)
    fun onClickLogin() = viewEvent(ON_CLICK_LOGIN)
    fun onClickNext() = viewEvent(ON_CLICK_NEXT)
    fun onClickStudentCode() = viewEvent(ON_CLICK_STUDENT_CODE)
    fun onClickBackground() = viewEvent(ON_CLICK_BACKGROUND)

    companion object{
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_LOGIN = "ON_CLICK_LOGIN"
        const val ON_CLICK_NEXT = "ON_CLICK_NEXT"
        const val ON_CLICK_STUDENT_CODE = "ON_CLICK_STUDENT_CODE"
        const val ON_CLICK_BACKGROUND = "ON_CLICK_BACKGROUND"
    }
}