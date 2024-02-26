package com.b1nd.alimo.presentation.feature.main.detail

import android.util.Log
import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(

): BaseViewModel() {

    fun onClickBack() {
        Log.d("TAG", "onClickBack: ")
        viewEvent(ON_CLICK_BACK)
    }

    fun onClickSend() = viewEvent(ON_CLICK_SEND)

    fun onClickBookmark() = viewEvent(ON_CLICK_BOOKMARK)

    fun onClickOkay() = viewEvent(ON_CLICK_OKAY)

    fun onClickLove() = viewEvent(ON_CLICK_LOVE)

    fun onClickLaugh() = viewEvent(ON_CLICK_LAUGH)

    fun onClickSad() = viewEvent(ON_CLICK_SAD)

    fun onClickAngry() = viewEvent(ON_CLICK_ANGRY)

    companion object {
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_SEND = "ON_CLICK_SEND"
        const val ON_CLICK_BOOKMARK = "ON_CLICK_BOOKMARK"
        const val ON_CLICK_OKAY = "ON_CLICK_OKAY"
        const val ON_CLICK_LOVE = "ON_CLICK_LOVE"
        const val ON_CLICK_LAUGH = "ON_CLICK_LAUGH"
        const val ON_CLICK_SAD = "ON_CLICK_SAD"
        const val ON_CLICK_ANGRY = "ON_CLICK_ANGRY"
    }
}