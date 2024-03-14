package com.b1nd.alimo.presentation.feature.main.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.model.NotificationModel
import com.b1nd.alimo.data.model.SpeakerModel
import com.b1nd.alimo.data.repository.HomeRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
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
    private val _category = MutableStateFlow("전체")

    val pagingData = _category.flatMapLatest {
        flow {
            try {
                emitAll(repository.getPost(it).cachedIn(viewModelScope))
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("TAG", ": ${e.message}")
                emitAll(emptyFlow<PagingData<NotificationModel>>())
            }
        }
    }

    private val _sideEffect = Channel<HomeSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _speakerData: MutableStateFlow<SpeakerModel?> = MutableStateFlow(null)
    val speakerData = _speakerData.asStateFlow()

    private val _categoryData: MutableStateFlow<List<HomeCategoryRvItem>> = MutableStateFlow(emptyList())
    val categoryData = _categoryData.asStateFlow()

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
                    _speakerData.value =  it.data?.data?.toModel()
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
        category: String
    ) {
        Log.d("TAG", "setCategory: $category")
        _category.value = category
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