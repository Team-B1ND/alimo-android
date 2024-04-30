package com.b1nd.alimo.presentation.feature.main.home

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.data.model.SpeakerModel
import com.b1nd.alimo.data.repository.HomeRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import com.b1nd.alimo.presentation.utiles.Dlog
import com.b1nd.alimo.presentation.utiles.Env.NETWORK_ERROR_MESSAGE
import com.b1nd.alimo.presentation.utiles.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): BaseViewModel() {

    // 현재 선택된 카테고리에 대한 정보
    private val _chooseCategory = MutableStateFlow(Pair("전체", 0))
    val chooseCategory = _chooseCategory.asStateFlow()

    val pagingData = _chooseCategory.flatMapLatest {
        flow {
            try {
                emitAll(repository.getPost(it.first).cachedIn(viewModelScope))
            } catch (e: Exception) {
                e.printStackTrace()
                Dlog.e(e.message)
                emitAll(emptyFlow<PagingData<NotificationModel>>())
            }
        }
    }

    private val _sideEffect = Channel<HomeSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    // 상단 공지에 대한 정보
    private val _speakerData: MutableStateFlow<SpeakerModel?> = MutableStateFlow(null)
    val speakerData = _speakerData.asStateFlow()

    // 상단 카테고리들에 대한 정보
    private val _categoryData: MutableStateFlow<List<HomeCategoryRvItem>> = MutableStateFlow(emptyList())
    val categoryData = _categoryData.asStateFlow()

    // 중복 에러 방출을 방지하기 위한 카운트
    private val _errorCount = MutableStateFlow(0)
    val errorCount = _errorCount.asStateFlow()

    fun loadMyCategory() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategory().collectLatest {
                when(it) {
                    is Resource.Error -> {
                        if (it.error?.message == "Network is unreachable") {
                            addErrorCount()
                        }
                        _sideEffect.send(HomeSideEffect.NotFound(HomeFound.Category))
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        if (it.data == null) {
                            _sideEffect.send(HomeSideEffect.NotFound(HomeFound.Category))
                            return@collectLatest
                        }
                        _errorCount.value = 0
                        _categoryData.value = it.data.roles.map { category ->
                            HomeCategoryRvItem(category, false)
                        }
                    }
                }
            }
        }

    fun loadSpeaker() = launchIO {
        repository.getSpeaker().collectLatest {
            when(it) {
                is Resource.Success -> {
                    _speakerData.value =  it.data
                }
                is Resource.Loading -> {}

                is Resource.Error -> {
                    if (it.error?.message == "Network is unreachable") {
                        return@collectLatest addErrorCount()
                    }
                    _sideEffect.send(HomeSideEffect.NotFound(HomeFound.Speaker))
                }
            }
        }
    }

    fun setCategory(
        category: String,
        position: Int
    ) {
        Dlog.d("setCategory: $category")
        _chooseCategory.value = Pair(category, position)
    }

    fun addErrorCount() {
        if (errorCount.value == 0) {
            launchIO {
                _sideEffect.send(HomeSideEffect.NetworkError(NETWORK_ERROR_MESSAGE))
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
                    _sideEffect.send(HomeSideEffect.FailedChangeEmoji)
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
                    _sideEffect.send(HomeSideEffect.FailedChangeBookmark)
                }
            }
        }
    }

    fun onClickSpeaker() =
        viewEvent(ON_CLICK_SPEAKER)


    companion object {
        const val ON_CLICK_SPEAKER = "ON_CLICK_SPEAKER"
    }
}