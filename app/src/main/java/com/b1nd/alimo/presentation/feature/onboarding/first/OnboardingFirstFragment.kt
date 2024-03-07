package com.b1nd.alimo.presentation.feature.onboarding.first


import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentOnboardingFirstBinding
import com.b1nd.alimo.presentation.MainActivity
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.utiles.collectStateFlow
import com.b1nd.alimo.presentation.utiles.startActivityWithFinishAll
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFirstFragment:BaseFragment<FragmentOnboardingFirstBinding, OnboardingFirstViewModel>(R.layout.fragment_onboarding_first) {
    override val viewModel: OnboardingFirstViewModel by viewModels()

    override fun initView() {
        viewModel.tokenCheck()

        // Launch Screen Time(2m)
        Handler().postDelayed({
            collectStateFlow(viewModel.tokenState) {
                // Refresh토큰이 만료됐는지 않됐거나 없는지 확인
                // 만료됐다면 Text에 "만료"를 넣어서 보냄
                Log.d("TAG", "initView: $it")
                if (it.token == "만료" || it.token == null) {
                    val direction =
                        OnboardingFirstFragmentDirections.actionOnboardingFirstToOnboardingSecond(
                            it.token
                        )
                    findNavController().navigate(direction)
                }
                // 토큰이 있다면 MainActivity로 이동
                else{
                    startActivityWithFinishAll(MainActivity::class.java)

                }
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