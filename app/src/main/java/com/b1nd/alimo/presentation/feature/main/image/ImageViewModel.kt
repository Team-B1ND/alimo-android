package com.b1nd.alimo.presentation.feature.main.image

import android.util.Log
import com.b1nd.alimo.data.model.DetailNotificationModel
import com.b1nd.alimo.data.model.EmojiModel
import com.b1nd.alimo.data.repository.DetailRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.feature.main.detail.DetailSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
   private val detailRepository: DetailRepository
): BaseViewModel() {

    private val _notificationState = MutableStateFlow<DetailNotificationModel?>(null)
    val notificationState = _notificationState.asStateFlow()

    private val _emojiState = MutableStateFlow<List<EmojiModel>>(emptyList())
    val emojiState = _emojiState.asStateFlow()

    private val _sideEffect = Channel<DetailSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()


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