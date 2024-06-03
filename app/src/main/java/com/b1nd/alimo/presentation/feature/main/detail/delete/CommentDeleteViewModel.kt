package com.b1nd.alimo.presentation.feature.main.detail.delete

import com.b1nd.alimo.presentation.base.BaseViewModel

class CommentDeleteViewModel: BaseViewModel() {


    fun onClickDelete() = viewEvent(ON_CLICK_DELETE)

    fun onClickClose() = viewEvent(ON_CLICK_CLOSE)

    companion object {
        const val ON_CLICK_DELETE = "ON_CLICK_DELETE"
        const val ON_CLICK_CLOSE = "ON_CLICK_CLOSE"
    }
}