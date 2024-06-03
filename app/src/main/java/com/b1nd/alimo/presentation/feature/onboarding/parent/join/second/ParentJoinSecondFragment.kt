package com.b1nd.alimo.presentation.feature.onboarding.parent.join.second

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentParentJoinSecondBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.second.ParentJoinSecondViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.second.ParentJoinSecondViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.second.ParentJoinSecondViewModel.Companion.ON_CLICK_LOGIN
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.second.ParentJoinSecondViewModel.Companion.ON_CLICK_NEXT
import com.b1nd.alimo.presentation.utiles.Dlog
import com.b1nd.alimo.presentation.utiles.Env
import com.b1nd.alimo.presentation.utiles.collectFlow
import com.b1nd.alimo.presentation.utiles.hideKeyboard
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import com.b1nd.alimo.presentation.utiles.shortToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParentJoinSecondFragment :
    BaseFragment<FragmentParentJoinSecondBinding, ParentJoinSecondViewModel>(
        R.layout.fragment_parent_join_second
    ) {
    override val viewModel: ParentJoinSecondViewModel by viewModels()
    private val args: ParentJoinSecondFragmentArgs by navArgs()


    override fun initView() {
        // 학생 이름 가져오는 기능
        viewModel.setStudentCode(args.childeCode)
        initSideEffect()

        collectFlow(viewModel.isButtonClicked){
            if (it){
                mBinding.progressCir.visibility = View.VISIBLE
                mBinding.loginBtnOn.visibility = View.INVISIBLE
                mBinding.progressCir.setIndeterminate(it)
            }else{
                mBinding.progressCir.visibility = View.INVISIBLE
                mBinding.loginBtnOn.visibility = View.VISIBLE
            }
        }
        

        lifecycleScope.launch {
            viewModel.memberName.collect{
                mBinding.parentName.text = it.name
            }
        }

        bindingViewEvent { event ->
            event.onSuccessEvent {
                when (it) {
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack(R.id.parentJoinSecond, false)
                        findNavController().navigate(R.id.action_parentJoinSecond_to_onboardingThird)
                    }

                    ON_CLICK_LOGIN -> {
                        findNavController().navigate(R.id.action_parentJoinSecond_to_parentLoginFirst)
                    }

                    ON_CLICK_NEXT -> {
                        // 비번과 비번확인의 Text가 같으면 회원가입 실행 아니면 오류 Message
                        if (comparisonPassword()) {
//                            findNavController().navigate(R.id.action_parentJoinSecond_to_parentJoinThird)
                            viewModel.singUp(
                                email = mBinding.idEditText.text.toString(),
                                password = mBinding.pwEditText.text.toString(),
                                childCode = args.childeCode,
                                memberId = args.memberId
                            )
                        } else {
                            mBinding.errorText.visibility = View.VISIBLE
                        }
                    }

                    ON_CLICK_BACKGROUND -> {
                        Dlog.d("initView: background")
                        mBinding.idEditTextLayout.clearFocus()
                        mBinding.pwEditTextLayout.clearFocus()
                        view?.hideKeyboard()
                    }



                }
            }
        }
        mBinding.idEditTextLayout.setEndIconOnClickListener {
            mBinding.idEditText.text = null
        }

        // TextWatcher를 이용하여 EditText의 텍스트 변화 감지
        mBinding.idEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (!isProgressBarVisible()) {
                    updateButtonColor()
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

        mBinding.pwEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (!isProgressBarVisible()) {
                    updateButtonColor()
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        })

        mBinding.verifyPwEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                charSequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (!isProgressBarVisible()) {
                    updateButtonColor()
                }
            }

            override fun afterTextChanged(editable: Editable?) {}
        })
    }

    private fun updateButtonColor() {
        if (isProgressBarVisible()) return // 프로그레스바가 보이면 종료

        val text1 = mBinding.idEditText.text.toString().trim { it <= ' ' }
        val text2 = mBinding.pwEditText.text.toString().trim { it <= ' ' }
        val text3 = mBinding.verifyPwEditText.text.toString().trim { it <= ' ' }

        // 버튼의 색상을 변경하는 로직 추가
        if (text1.isNotEmpty() && text2.isNotEmpty() && text3.isNotEmpty()) {
            Dlog.d("updateButtonColor: on")
            // 두 EditText의 텍스트가 null이 아닐 때 버튼의 색상을 변경
            mBinding.loginBtnOff.visibility = View.INVISIBLE
            mBinding.loginBtnOn.visibility = View.VISIBLE
        } else {
            Dlog.d("updateButtonColor: off")
            // 두 EditText 중 하나라도 텍스트가 null일 때 버튼의 색상을 기본 색상으로 변경
            mBinding.loginBtnOff.visibility = View.VISIBLE
            mBinding.loginBtnOn.visibility = View.INVISIBLE
        }
    }


    // 비번과 비번확인 Text가 같은지 확인하는 함수
    private fun comparisonPassword(): Boolean {
        val password = mBinding.pwEditText.text.toString()
        val verifyPassword = mBinding.verifyPwEditText.text.toString()
        Dlog.d("comparisonPassword: ${ password == verifyPassword }")
        return password == verifyPassword
    }

    private fun initSideEffect(){
        collectFlow(viewModel.parentJoinSecondSideEffect){
            when(it){
                is ParentJoinSecondSideEffect.FailedSignup ->{
                }
                is ParentJoinSecondSideEffect.FailedMemberName ->{
                    requireContext().shortToast(Env.ERROR)
                }
                ParentJoinSecondSideEffect.SuccessSignup ->{
                    val email = mBinding.idEditText.text.toString()
                    val direction =
                        ParentJoinSecondFragmentDirections.actionParentJoinSecondToParentJoinThird(
                            email
                        )
                    findNavController().navigate(direction)
                }
            }
        }
    }

    private fun isProgressBarVisible(): Boolean {
        return mBinding.progressCir.visibility == View.VISIBLE
    }

}