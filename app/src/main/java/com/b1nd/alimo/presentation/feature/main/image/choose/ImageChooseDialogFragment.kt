package com.b1nd.alimo.presentation.feature.main.image.choose

import androidx.fragment.app.viewModels
import com.b1nd.alimo.R
import com.b1nd.alimo.databinding.DialogImageChooseBinding
import com.b1nd.alimo.presentation.base.BaseDialogFragment
import com.b1nd.alimo.presentation.feature.main.image.choose.ImageChooseDialogViewModel.Companion.ON_CLICK_SAVE_ALL
import com.b1nd.alimo.presentation.feature.main.image.choose.ImageChooseDialogViewModel.Companion.ON_CLICK_THAT_ALL
import com.b1nd.alimo.presentation.utiles.onSuccessEvent

class ImageChooseDialogFragment (
    private val onClickSaveAll: () -> Unit,
    private val onClickSaveThat: () -> Unit,
): BaseDialogFragment<DialogImageChooseBinding, ImageChooseDialogViewModel>(R.layout.dialog_image_choose) {

    override val viewModel: ImageChooseDialogViewModel by viewModels()

    override fun initView() {

        bindingViewEvent {
            it.onSuccessEvent {
                when(it) {
                    ON_CLICK_SAVE_ALL -> {
                        onClickSaveAll()
                        dismiss()
                    }
                    ON_CLICK_THAT_ALL -> {
                        onClickSaveThat()
                        dismiss()
                    }
                }
            }
        }
    }
}