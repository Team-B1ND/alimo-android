package com.b1nd.alimo.presentation.feature.main.image

import android.util.Log
import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(

): BaseViewModel() {


    fun onClickBack() {
        Log.d("TAG", "onClickBack: ")
        viewEvent(ON_CLICK_BACK)
    }

    fun onClickDownload() = viewEvent(ON_CLICK_DOWNLOAD)

    companion object {
        const val ON_CLICK_BACK = "ON_CLICK_BACK"
        const val ON_CLICK_DOWNLOAD = "ON_CLICK_DOWNLOAD"


        const val NOT_BOOKMARK = "NOT_BOOKMARK"
        const val BOOKMARK = "BOOKMARK"
    }
}