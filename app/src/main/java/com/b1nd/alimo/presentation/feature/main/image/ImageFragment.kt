package com.b1nd.alimo.presentation.feature.main.image

import android.annotation.SuppressLint
import android.os.Build
import android.os.Build.VERSION_CODES
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsetsController
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.b1nd.alimo.R
import com.b1nd.alimo.data.model.FileModel
import com.b1nd.alimo.databinding.FragmentImageBinding
import com.b1nd.alimo.presentation.base.BaseFragment
import com.b1nd.alimo.presentation.feature.main.MainActivity
import com.b1nd.alimo.presentation.feature.main.image.ImageViewModel.Companion.ON_CLICK_BACK
import com.b1nd.alimo.presentation.feature.main.image.ImageViewModel.Companion.ON_CLICK_DOWNLOAD
import com.b1nd.alimo.presentation.utiles.onSuccessEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageFragment: BaseFragment<FragmentImageBinding, ImageViewModel>(R.layout.fragment_image) {

    override val viewModel: ImageViewModel by viewModels()

    override fun initView() {
        (requireActivity() as? MainActivity)?.bottomVisible(false)
        initTouch()
        initPager()

        bindingViewEvent {
            it.onSuccessEvent {
                when(it) {
                    ON_CLICK_BACK -> {
                        findNavController().popBackStack()
                    }
                    ON_CLICK_DOWNLOAD -> {

                    }
                }
            }
        }
    }

    private fun initPager() {
        val adapter = ImageViewPagerAdapter(
            listOf(
                FileModel(
                    "https://s3-alimo.s3.ap-northeast-2.amazonaws.com/notification_images/e314d7ca-a429-4358-bea0-c6889f14834b_A4_-_1.png",
                    "",
                    0,
                    "",
                    ""
                ),
                FileModel(
                    "https://s3-alimo.s3.ap-northeast-2.amazonaws.com/notification_images/e314d7ca-a429-4358-bea0-c6889f14834b_A4_-_1.png",
                    "",
                    0,
                    "",
                    ""
                )
            )
        )
        mBinding.pagerImage.adapter = adapter
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initTouch() {
        val motionFunction: (MotionEvent) -> Unit = { motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                mBinding.run {
                    layoutTopBar.visibility = if (layoutTopBar.isVisible) View.INVISIBLE else View.VISIBLE
                    layoutBottomBar.visibility = if (layoutBottomBar.isVisible) View.INVISIBLE else View.VISIBLE
                }
            }
        }
        mBinding.layoutParent.setOnTouchListener { view, motionEvent ->
            motionFunction(motionEvent)
            return@setOnTouchListener true
        }
        mBinding.pagerImage.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                mBinding.run {
                    layoutTopBar.visibility =
                        if (layoutTopBar.isVisible) View.INVISIBLE else View.VISIBLE
                    layoutBottomBar.visibility =
                        if (layoutBottomBar.isVisible) View.INVISIBLE else View.VISIBLE
                }
            }
            return@setOnTouchListener true
        }
//        mBinding.imageContent.setOnTouchListener { view, motionEvent ->
//            motionFunction(motionEvent)
//            return@setOnTouchListener false
//        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as? MainActivity)?.bottomVisible(false)
        systemBarDark(true)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as? MainActivity)?.bottomVisible(true)
//        activity?.window?.statusBarColor = context?.getColor(R.color.white)?: 0
        systemBarDark(false)
    }

    private fun systemBarDark(isDark: Boolean) {
        if (Build.VERSION.SDK_INT >= VERSION_CODES.R) {
            activity?.window?.statusBarColor = if (isDark) requireContext().getColor(R.color.black) else requireContext().getColor(R.color.white)
            activity?.window?.insetsController?.setSystemBarsAppearance(
                if (isDark) 0 else WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            activity?.window?.decorView?.systemUiVisibility = if(isDark) 0 else View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

}