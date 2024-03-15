package com.b1nd.alimo.presentation.feature.main.image

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.ImageModel
import com.b1nd.alimo.data.repository.ImageRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repository: ImageRepository
): BaseViewModel() {

    private val _state = MutableStateFlow<ImageModel?>(null)
    val state = _state.asStateFlow()

    private val _sideEffect = Channel<ImageSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()


    fun getNotificationImage(
        notificationId: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        repository.getNotification(
            notificationId = notificationId
        ).collectLatest {
            when(it) {
                is Resource.Success -> {
                    _state.value = it.data
                }
                is Resource.Loading -> {}
                is Resource.Error -> {
                    _sideEffect.send(ImageSideEffect.FailedNotificationLoad(it.error?: Throwable()))
                }
            }
        }
    }

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