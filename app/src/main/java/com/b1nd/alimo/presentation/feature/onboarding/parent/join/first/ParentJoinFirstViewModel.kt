package com.b1nd.alimo.presentation.feature.onboarding.parent.join.first

import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.repository.ParentJoinRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.utiles.Dlog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParentJoinFirstViewModel @Inject constructor(
    private val parentJoinRepository: ParentJoinRepository
): BaseViewModel() {

    private val _trueFalse =  MutableStateFlow(ParentJoinFirstModel())
    val trueFalse = _trueFalse.asStateFlow()

    private val _parentJoinSideEffect = Channel<ParentJoinFirstSideEffect>()
    val parentJoinFirstSideEffect = _parentJoinSideEffect.receiveAsFlow()


    private val _isButtonClicked = MutableStateFlow<Boolean>(true)
    val isButtonClicked = _isButtonClicked.asStateFlow()

    // 학생 코드 인증
    fun checkStudentCode(studentCode: String){
        viewModelScope.launch {
            parentJoinRepository.childCode(studentCode).collectLatest {resource ->
                when(resource){
                    is Resource.Error ->{
                        _parentJoinSideEffect.send(ParentJoinFirstSideEffect.FailedChildCode(resource.error ?: Throwable()))
                        Dlog.d("실패: ")
                    }
                    is Resource.Success ->{
                        val newEffect = ParentJoinFirstModel(resource.data?.isCorrectChildCode, resource.data?.memberId)
                        _trueFalse.value = _trueFalse.value.copy(newEffect.isCorrectChildCode, newEffect.memberId)
                        delay(5000) // 2초 후 버튼 클릭 상태 초기화
                        _isButtonClicked.value = true
                        Dlog.d("성공: ${resource.data}")
                    }
                    is Resource.Loading ->{
                        Dlog.d("로딩: ")
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