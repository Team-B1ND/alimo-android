package com.b1nd.alimo.presentation.feature.main.detail

import android.util.Log
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.DetailNotificationModel
import com.b1nd.alimo.data.repository.DetailRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.utiles.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
   private val detailRepository: DetailRepository
): BaseViewModel() {

    private val _notificationState = MutableStateFlow<DetailNotificationModel?>(null)
    val notificationState = _notificationState.asStateFlow()

    private val _sideEffect = Channel<DetailSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun loadNotification(
        notificationId: Int
    ) = launchIO {
        detailRepository.loadNotification(
            notificationId = notificationId
        ).collectLatest {
            when(it) {
                is Resource.Success -> {
                    _notificationState.value = it.data?.data
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    _sideEffect.send(DetailSideEffect.FailedNotificationLoad(it.error?: Throwable()))
                }
            }
        }
    }

    fun setEmoji(
        notificationId: Int,
        reaction: String
    ) = launchIO {
        detailRepository.patchEmojiEdit(
            notificationId = notificationId,
            reaction = reaction
        ).collectLatest {
            when(it) {
                is Resource.Success -> {

                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    _sideEffect.send(DetailSideEffect.FailedChangeEmoji)
                }
            }
        }
    }

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