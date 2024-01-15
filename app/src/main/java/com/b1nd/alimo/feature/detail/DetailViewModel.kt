package com.b1nd.alimo.feature.detail

import android.util.Log
import com.b1nd.alimo.base.BaseViewModel

class DetailViewModel: BaseViewModel() {

    fun onClickBack() {
        Log.d("TAG", "onClickBack: ")
        viewEvent(ON_CLICK_BACK)
    }

    fun onClickSend() = viewEvent(ON_CLICK_SEND)

    companion object {
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_SEND = "ON_CLICK_SEND"
    }
}