package com.b1nd.alimo.presentation.feature.main.bookmark

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.repository.BookmarkRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.utiles.Env
import com.b1nd.alimo.presentation.utiles.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: BookmarkRepository
): BaseViewModel() {

    val pagingData = repository.getPost().cachedIn(viewModelScope)

    private val _sideEffect = Channel<BookmarkSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _errorCount = MutableStateFlow(0)
    val errorCount = _errorCount.asStateFlow()

    fun addErrorCount() {
        if (errorCount.value == 0) {
            launchIO {
                _sideEffect.send(BookmarkSideEffect.NetworkError(Env.NETWORK_ERROR_MESSAGE))
            }
        }
        _errorCount.value += 1
    }

    fun setEmoji(
        notificationId: Int,
        emoji: String
    ) = launchIO {
        repository.patchEmoji(
            notificationId = notificationId,
            emoji = emoji
        ).collectLatest {
            when(it) {
                is Resource.Success -> {

                }
                is Resource.Loading -> {}
                is Resource.Error -> {
                    if (it.error?.message == "Network is unreachable") {
                        return@collectLatest addErrorCount()
                    }
                    _sideEffect.send(BookmarkSideEffect.FailedChangeEmoji)
                }
            }
        }
    }

    fun patchBookmark(
        notificationId: Int
    ) = launchIO {
        repository.patchBookmark(
            notificationId = notificationId
        ).collectLatest {
            when(it) {
                is Resource.Success -> {

                }
                is Resource.Loading -> {}
                is Resource.Error -> {
                    if (it.error?.message == "Network is unreachable") {
                        return@collectLatest addErrorCount()
                    }
                    _sideEffect.send(BookmarkSideEffect.FailedChangeBookmark)
                }
            }
        }
    }

}