package com.b1nd.alimo.presentation.feature.onboarding.first


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentOnboardingFirstBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFirstFragment:BaseFragment<FragmentOnboardingFirstBinding, OnboardingFirstViewModel>(R.layout.fragment_onboarding_first) {
    override val viewModel: OnboardingFirstViewModel by viewModels()

    override fun initView() {
        Handler().postDelayed({
            if (viewModel.tokenCheck()){
                findNavController().navigate(R.id.action_onboardingFirst_to_onboardingSecond)
            }else{
                findNavController().navigate(R.id.action_onboardingFirst_to_onboardingSecond)

//                startActivityWithFinishAll(MainActivity::class.java)
            }
        }, 2000)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.window?.statusBarColor = context?.getColor(R.color.Main500)?: 0

        return super.onCreateView(inflater, container, savedInstanceState)

    }

    override fun onStop() {
        super.onStop()

        activity?.window?.statusBarColor = context?.getColor(R.color.white)?: 0
    }
}