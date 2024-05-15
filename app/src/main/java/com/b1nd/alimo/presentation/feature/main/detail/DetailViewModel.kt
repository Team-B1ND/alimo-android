package com.b1nd.alimo.presentation.feature.main.detail

import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.EmojiModel
import com.b1nd.alimo.data.repository.DetailRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.utiles.Dlog
import com.b1nd.alimo.presentation.utiles.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
   private val detailRepository: DetailRepository
): BaseViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state = _state.asStateFlow()

    private val _emojiState = MutableStateFlow<List<EmojiModel>>(emptyList())
    val emojiState = _emojiState.asStateFlow()

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
                    _state.value = _state.value.copy(
                        isLoading = false,
                        notificationState = it.data
                    )
                }
                is Resource.Loading -> {

                }
                is Resource.Error -> {
                    _sideEffect.send(DetailSideEffect.FailedNotificationLoad(it.error?: Throwable()))
                }
            }
        }
    }

    fun pathBookmark(
        notificationId: Int
    ) = launchIO {
        detailRepository.pathBookmark(
            notificationId = notificationId
        ).collectLatest {
            when(it) {
                is Resource.Success -> {
                    _sideEffect.send(DetailSideEffect.SuccessChangeBookmark)
                }
                is Resource.Loading -> {}
                is Resource.Error -> {
                    _sideEffect.send(DetailSideEffect.FailedChangeBookmark(it.error?: Throwable()))
                }
            }
        }
    }

    fun loadEmoji(
        notificationId: Int
    ) = launchIO {
        detailRepository.loadEmoji(
            notificationId = notificationId
        ).collectLatest {
            when(it) {
                is Resource.Success -> {
                    _emojiState.value =  it.data ?: emptyList()
                }
                is Resource.Loading -> {}
                is Resource.Error -> {
                    _sideEffect.send(DetailSideEffect.FailedEmojiLoad(it.error?: Throwable()))
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
                is Resource.Success -> {}
                is Resource.Loading -> {}
                is Resource.Error -> {
                    _sideEffect.send(DetailSideEffect.FailedChangeEmoji)
                }
            }
        }
    }

    fun postSend(
        notificationId: Int,
        text: String,
        commentId: Int? = null
    ) = launchIO {
        detailRepository.postComment(
            notificationId = notificationId,
            text = text,
            commentId = commentId
        ).collectLatest {
            when(it) {
                is Resource.Success -> {
                    // postion받고 state 갱신
                    viewModelScope.launch(Dispatchers.IO) {
                        val load = async {
                            loadNotification(notificationId)
                        }
                        load.await()
                        _sideEffect.send(DetailSideEffect.SuccessAddComment)

                    }
                }
                is Resource.Loading -> {}
                is Resource.Error -> {
                    _sideEffect.send(DetailSideEffect.FailedPostComment(it.error?: Throwable()))
                }
            }
        }
    }

    fun onClickBack() {
        Dlog.d("onClickBack: ")
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


        const val NOT_BOOKMARK = "NOT_BOOKMARK"
        const val BOOKMARK = "BOOKMARK"

        const val CHOOSE = "CHOOSE"
        const val NOT_CHOOSE = "NOT_CHOOSE"
    }
}