package com.b1nd.alimo.presentation.feature.onboarding.parent.join.third

import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentParentJoinThirdBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.main.MainActivity
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_CERTIFICATION
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_CHECK
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_JOIN
import com.b1nd.alimo.presentation.utiles.Env
import com.b1nd.alimo.presentation.utiles.collectFlow
import com.b1nd.alimo.presentation.utiles.hideKeyboard
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import com.b1nd.alimo.presentation.utiles.shortToast
import com.b1nd.alimo.presentation.utiles.startActivityWithFinishAll
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ParentJoinThirdFragment :
    BaseFragment<FragmentParentJoinThirdBinding, ParentJoinThirdViewModel>(
        R.layout.fragment_parent_join_third
    ) {
    override val viewModel: ParentJoinThirdViewModel by viewModels()
    private val args: ParentJoinThirdFragmentArgs by navArgs()


    override fun initView() {
        initSideEffect()


        lifecycleScope.launch {
            // ParentJoinState상태에 따라 성공 혹은 실패
            viewModel.parentJoinState.collectLatest {
                val accessToken = it.accessToken
                val refreshToken = it.refreshToken
                if (accessToken != null && refreshToken != null) {
                    mBinding.joinBtnOff.visibility = View.INVISIBLE
                    mBinding.joinBtnOn.visibility = View.VISIBLE
                }
            }
        }
        bindingViewEvent { event ->
            event.onSuccessEvent {
                when (it) {
                    ON_CLICK_BACK -> {
                        findNavController().navigate(R.id.action_parentJoinThird_to_onboardingThird)
                    }

                    ON_CLICK_BACKGROUND -> {
                        mBinding.idEditTextLayout.clearFocus()
                        view?.hideKeyboard()
                    }

                    ON_CLICK_JOIN -> {
                        startActivityWithFinishAll(MainActivity::class.java)
                    }

                    ON_CLICK_CERTIFICATION -> {

                        viewModel.postEmail(args.email)

                        mBinding.check.visibility = View.VISIBLE
                        mBinding.time.visibility = View.VISIBLE
                        mBinding.certification.visibility = View.GONE

                        // 이메일 인증을 요청하면 5분 타이머 실행
                        object : CountDownTimer(300000, 1000) {

                            override fun onTick(millisUntilFinished: Long) {
                                val minutes = millisUntilFinished / 60000
                                val seconds = (millisUntilFinished % 60000) / 1000
                                val timeString = String.format("%d:%02d", minutes, seconds)
                                mBinding.time.text = timeString
                            }

                            override fun onFinish() {
                                mBinding.time.text = "0:00"
                                findNavController().navigate(R.id.action_parentJoinThird_to_onboardingThird)
                            }

                        }.start()

                    }

                    ON_CLICK_CHECK -> {
                        Dlog.d("initView: ${args.email} ${ mBinding.idEditText.text.toString()}")
                        // 서버로 인증 코드 전송
                        viewModel.emailCheck(
                            email = args.email,
                            code = mBinding.idEditText.text.toString()
                        )
                        view?.hideKeyboard()
                    }
                }
            }
        }




    }

    private fun initSideEffect(){
        collectFlow(viewModel.parentJoinThirdSideEffect){
            when(it){
                is ParentJoinThirdSideEffect.FailedEmailCheck ->{
                    mBinding.error.visibility = View.VISIBLE
                    Log.d("TAG", "initSideEffect: 이메일 인증 실패${it.throwable}")
                }
                is ParentJoinThirdSideEffect.FailedPostEmail ->{
                    requireContext().shortToast(Env.ERROR)
                    Log.d("TAG", "initSideEffect: ${it.throwable}")
                }
                ParentJoinThirdSideEffect.Success ->{
                    mBinding.error.visibility = View.INVISIBLE
                    requireContext().shortToast("이메일 인증에 성공하였습니다.")
                    Log.d("TAG", "initSideEffect: 성공")
                }
            }
        }
    }


}