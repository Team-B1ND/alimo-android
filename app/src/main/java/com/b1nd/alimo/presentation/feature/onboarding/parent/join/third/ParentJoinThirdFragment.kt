package com.b1nd.alimo.presentation.feature.onboarding.parent.join.third

import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentParentJoinThirdBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_BACKGROUND
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_CERTIFICATION
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_CHECK
import com.b1nd.alimo.presentation.feature.onboarding.parent.join.third.ParentJoinThirdViewModel.Companion.ON_CLICK_JOIN
import com.b1nd.alimo.presentation.utiles.hideKeyboard
import com.b1nd.alimo.presentation.utiles.onSuccessEvent

class ParentJoinThirdFragment :
    BaseFragment<FragmentParentJoinThirdBinding, ParentJoinThirdViewModel>(
        R.layout.fragment_parent_join_third
    ) {
    override val viewModel: ParentJoinThirdViewModel by viewModels()


    override fun initView() {
        bindingViewEvent { event ->
            event.onSuccessEvent {
                when (it) {
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack()
                    }

                    ON_CLICK_BACKGROUND -> {
                        mBinding.idEditTextLayout.clearFocus()
                        view?.hideKeyboard()
                    }

                    ON_CLICK_JOIN -> {
                        findNavController().navigate(R.id.action_parentJoinThirst_to_onboardingThird)
                    }

                    ON_CLICK_CERTIFICATION -> {
                        mBinding.check.visibility = View.VISIBLE
                        mBinding.time.visibility = View.VISIBLE
                        mBinding.certification.visibility = View.GONE


                        object : CountDownTimer(300000, 1000) {

                            override fun onTick(millisUntilFinished: Long) {
                                val minutes = millisUntilFinished / 60000
                                val seconds = (millisUntilFinished % 60000) / 1000
                                val timeString = String.format("%d:%02d", minutes, seconds)
                                mBinding.time.text = timeString
                            }

                            override fun onFinish() {
                                mBinding.time.text = "0:00"
                            }

                        }.start()

                    }

                    ON_CLICK_CHECK -> {
                        mBinding.joinBtnOff.visibility = View.INVISIBLE
                        mBinding.joinBtnOn.visibility = View.VISIBLE
                        view?.hideKeyboard()
                    }
                }
            }
        }

        mBinding.checkLayout.bringToFront()


    }


}