package com.b1nd.alimo.presentation.feature.onboarding.parent.join.first

import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.repository.ParentJoinRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.utiles.Dlog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParentJoinFirstViewModel @Inject constructor(
    private val parentJoinRepository: ParentJoinRepository
): BaseViewModel() {

    private val _trueFalse = MutableSharedFlow<ParentJoinFirstState>(replay = 0)
    val trueFalse: SharedFlow<ParentJoinFirstState> = _trueFalse.asSharedFlow()

    // 학생 코드 인증
    fun checkStudentCode(studentCode: String){
        viewModelScope.launch {
            parentJoinRepository.childCode(studentCode).catch {
                Dlog.d("checkStudentCode: ${it.message}")
            }.collectLatest {resource ->
                when(resource){
                    is Resource.Error ->{
                        val newEffect = ParentJoinFirstState(null)
                        _trueFalse.emit(newEffect)
                    }
                    is Resource.Success ->{
                        val newEffect = ParentJoinFirstState(resource.data)
                        _trueFalse.emit(newEffect)
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