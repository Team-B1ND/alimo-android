package com.b1nd.alimo.presentation.feature.onboarding.second

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentOnboardingSecondBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.custom.CustomSnackBar
import com.b1nd.alimo.presentation.feature.onboarding.second.OnboardingSecondViewModel.Companion.ON_CLICK_START
import com.b1nd.alimo.presentation.utiles.collectStateFlow
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingSecondFragment:
    BaseFragment<FragmentOnboardingSecondBinding, OnboardingSecondViewModel>(
    R.layout.fragment_onboarding_second) {
    override val viewModel: OnboardingSecondViewModel by viewModels()
    private val args: OnboardingSecondFragmentArgs by navArgs()

    override fun initView() {
        if(args.token == "만료"){
            CustomSnackBar.make(requireView(), "세션이 만료 되었어요").show()
        }
        viewModel.alarmCheck()

        collectStateFlow(viewModel.alarmState){
            Log.d("TAG", "알림 $it ")
            if(it == false){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // Q는 Android 10을 나타냅니다.
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 30)
                    }
                }
            }
        }


        bindingViewEvent { event ->
            event.onSuccessEvent {
                when (it) {
                    ON_CLICK_START -> {
                        findNavController().navigate(R.id.action_onboardingSecond_to_onboardingThird)

                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 30) { // 알림 권한 요청 코드인지 확인합니다.
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 알림 권한이 허용된 경우
                // 권한이 허용되었을 때 실행할 작업을 여기에 추가합니다.
                viewModel.setAlarm(true)
//                Toast.makeText(requireContext(), "알림 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 알림 권한이 거부된 경우
                // 권한이 거부되었을 때 실행할 작업을 여기에 추가합니다.
                viewModel.setAlarm(false)
//                Toast.makeText(requireContext(), "알림 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}