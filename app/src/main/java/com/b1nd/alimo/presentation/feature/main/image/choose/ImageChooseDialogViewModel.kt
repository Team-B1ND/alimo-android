package com.b1nd.alimo.presentation.feature.main.image.choose

import com.b1nd.alimo.presentation.base.BaseViewModel

class ImageChooseDialogViewModel: BaseViewModel() {

    fun onClickSaveAll() =
        viewEvent(ON_CLICK_SAVE_ALL)

    fun onClickThatAll() =
        viewEvent(ON_CLICK_THAT_ALL)

    companion object {
        const val ON_CLICK_SAVE_ALL = "ON_CLICK_SAVE_ALL"
        const val ON_CLICK_THAT_ALL = "ON_CLICK_THAT_ALL"

    }
}