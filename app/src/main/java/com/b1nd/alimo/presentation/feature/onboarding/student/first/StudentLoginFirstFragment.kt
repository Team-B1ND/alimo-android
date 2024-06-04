package com.b1nd.alimo.presentation.feature.onboarding.student.first

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentStudentLoginFirstBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.main.MainActivity
import com.b1nd.alimo.presentation.feature.onboarding.student.first.StudentLoginViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.onboarding.student.first.StudentLoginViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.presentation.feature.onboarding.student.first.StudentLoginViewModel.Companion.ON_CLICK_LOGIN_ON
import com.b1nd.alimo.presentation.utiles.Dlog
import com.b1nd.alimo.presentation.utiles.collectFlow
import com.b1nd.alimo.presentation.utiles.hideKeyboard
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import com.b1nd.alimo.presentation.utiles.shortToast
import com.b1nd.alimo.presentation.utiles.startActivityWithFinishAll
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudentLoginFirstFragment:
    BaseFragment<FragmentStudentLoginFirstBinding, StudentLoginViewModel>(
    R.layout.fragment_student_login_first
) {
    override val viewModel: StudentLoginViewModel by viewModels()

    override fun initView() {
        initSideEffect()
        // DAuth를 사용해서 코드를 가져온다면 로그인 실행
        lifecycleScope.launch {
            viewModel.dodamCode.collect{
                val code = it.code
                Dlog.d("cccccccccc: ")
                if (code != null){
                    viewModel.
                    login(code)
                    Dlog.d("initView: ${code}")
                }
                
            }


        }

        collectFlow(viewModel.isButtonClicked){
            if (it){
                mBinding.progressCir.visibility = View.VISIBLE
                mBinding.loginBtnOn.visibility = View.INVISIBLE
                mBinding.progressCir.setIndeterminate(it)
                mBinding.idEditText.isEnabled = false
                mBinding.pwEditText.isEnabled = false
            }else{
                mBinding.progressCir.visibility = View.INVISIBLE
                mBinding.loginBtnOn.visibility = View.VISIBLE
                mBinding.idEditText.isEnabled = true
                mBinding.pwEditText.isEnabled = true
            }
        }

        // 로그인을 실행하고 됐는지 않됐는지 확인
        lifecycleScope.launch {
            viewModel.loginState.collect{
                if(it.accessToken == null && it.refreshToken == null){
                    Dlog.d("로그인 실패: ${it.accessToken} ${it.refreshToken}")
                }else{
                    startActivityWithFinishAll(MainActivity::class.java)
                    Dlog.d("${it.accessToken}, ${it.refreshToken} ")
                }
            }
        }


        bindingViewEvent { event ->
            event.onSuccessEvent {
                when(it){
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack()
                    }
                    ON_CLICK_LOGIN_ON -> {
                        val id = mBinding.idEditText.text.toString()
                        val pw = mBinding.pwEditText.text.toString()
                        viewModel.getCode(id, pw)
                    }
                    ON_CLICK_BACKGROUND -> {
                        Dlog.d("initView: background")
                        mBinding.idEditText.clearFocus()
                        mBinding.pwEditText.clearFocus()
                        view?.hideKeyboard()
                    }
                }
            }
        }
        // Text Delete Icon Click Event
        mBinding.idEditTextLayout.setEndIconOnClickListener {
            mBinding.idEditTextLayout.editText?.text = null
        }
        // TextWatcher를 이용하여 EditText의 텍스트 변화 감지
        mBinding.idEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                if (mBinding.progressCir.visibility != View.VISIBLE) {
                    updateButtonColor()
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

        mBinding.pwEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                if (mBinding.progressCir.visibility != View.VISIBLE) {
                    updateButtonColor()
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        })
    }


    // InputTextLayout에 글자가 있다면 다음 Fragmnet록 가는 버튼 활성화
    private fun updateButtonColor() {
        val text1 = mBinding.idEditText.text.toString().trim { it <= ' ' }
        val text2 = mBinding.pwEditText.text.toString().trim { it <= ' ' }

        // 프로그래스바가 보이지 않는 상태에서만 버튼의 상태를 변경
        if (mBinding.progressCir.visibility != View.VISIBLE) {
            if (text1.isNotEmpty() && text2.isNotEmpty()) {
                Dlog.d("updateButtonColor: on")
                mBinding.loginBtnOff.visibility = View.GONE
                mBinding.loginBtnOn.visibility = View.VISIBLE
            } else {
                Dlog.d("updateButtonColor: off")
                mBinding.loginBtnOff.visibility = View.VISIBLE
                mBinding.loginBtnOn.visibility = View.GONE
            }
        }
    }

    private fun initSideEffect() {
        collectFlow(viewModel.studentLoginSideEffect){
            when(it){
                is StudentLoginSideEffect.FailedLogin -> {
                    requireContext().shortToast("아이디와 비빌번호를 다시 확인해주세요")
                }

                is StudentLoginSideEffect.FailedDAuth ->{
                   requireContext().shortToast("아이디와 비빌번호를 다시 확인해주세요")
                }
            }
        }
    }




}