package com.b1nd.alimo.presentation.feature.main.profile

import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.b1nd.alimo.BuildConfig
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.FragmentProfileBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.custom.CustomCategoryCard
import com.b1nd.alimo.presentation.custom.CustomSnackBar
import com.b1nd.alimo.presentation.feature.main.profile.ProfileViewModel.Companion.ON_CLICK_LOGOUT
import com.b1nd.alimo.presentation.feature.main.profile.ProfileViewModel.Companion.ON_CLICK_PRIVATE_POLICY
import com.b1nd.alimo.presentation.feature.main.profile.ProfileViewModel.Companion.ON_CLICK_SERVICE_POLICY
import com.b1nd.alimo.presentation.feature.main.profile.ProfileViewModel.Companion.ON_CLICK_STUDENT_CODE
import com.b1nd.alimo.presentation.feature.main.profile.ProfileViewModel.Companion.ON_CLICK_WITHDRAWAL
import com.b1nd.alimo.presentation.feature.main.profile.student.ProfileStudentClickListener
import com.b1nd.alimo.presentation.feature.main.profile.student.ProfileStudentCodeDialog
import com.b1nd.alimo.presentation.feature.main.profile.withdrawal.ProfileWithdrawalClickListener
import com.b1nd.alimo.presentation.feature.main.profile.withdrawal.ProfileWithdrawalDialog
import com.b1nd.alimo.presentation.utiles.collectFlow
import com.b1nd.alimo.presentation.utiles.collectStateFlow
import com.b1nd.alimo.presentation.utiles.loadImage
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment:
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile),
    ProfileStudentClickListener,
    ProfileWithdrawalClickListener
{

    override val viewModel: ProfileViewModel by viewModels()
    private val dialog: ProfileStudentCodeDialog by lazy {
        ProfileStudentCodeDialog(this, viewModel.state.value.data?.childCode)
    }

    private val withdrawalDialog: ProfileWithdrawalDialog by lazy {
        ProfileWithdrawalDialog(this)
    }
    override fun initView() {

        collectStateFlow(viewModel.state) {
            lifecycleScope.launch(Dispatchers.Main) {
                it.data?.let { model ->
                    if (model.image != null) {
                        Log.d("TAG", "initView: 엄 이미지 바ㅏ인딩")
                        mBinding.imageProfile.loadImage(model.image)
                    }
                    if (model.childCode != null) {
                        mBinding.textStudentCode.visibility = View.VISIBLE
                    }
                    mBinding.textUserName.text = model.name
                }
                if (!it.isAdd) {
                    it.category?.forEach { name ->
                        val card = CustomCategoryCard(requireContext(), null, name)
                        mBinding.layoutCategory.addView(card)
                    }
                }
            }

        }

        collectFlow(viewModel.sideEffect) {
            when(it) {
                is ProfileSideEffect.Success -> {

                }
                is ProfileSideEffect.FailedLoad -> {
                    Toast.makeText(requireContext(), "로딩에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        mBinding.cardVersion.setDescriptionText(BuildConfig.VERSION_NAME)
        mBinding.textStudentCode.paintFlags = Paint.UNDERLINE_TEXT_FLAG
//        val charset = ('0'..'9') + ('a'..'z') + ('A'..'Z')
//        for (i in 1..20) {
//            val randomName = List(Random.nextInt(1, 7)) { charset.random() }.joinToString().replace(", ", "")
//            val card = CustomCategoryCard(requireContext(), null, randomName)
//            card.setPadding(0, 8, 8, 0)
//            mBinding.layoutCategory.addView(card)
//        }

        bindingViewEvent {  event ->
            event.onSuccessEvent {
                when(it) {
                    ON_CLICK_STUDENT_CODE -> {
                        dialog.show(super.getChildFragmentManager(), "dialog")
                    }
                    ON_CLICK_PRIVATE_POLICY -> {

                    }
                    ON_CLICK_SERVICE_POLICY -> {

                    }
                    ON_CLICK_LOGOUT -> {

                    }
                    ON_CLICK_WITHDRAWAL -> {
                        withdrawalDialog.show(super.getChildFragmentManager(), "withdrawalDialog")
                    }
                }
            }
        }

        mBinding.cardAlarm.setSwitchOnClickListener {

        }
    }

    override fun onCopy() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            CustomSnackBar(requireView(), "복사에 성공하였습니다!").show()
        }
    }

    override fun onWithdrawal() {

    }
}