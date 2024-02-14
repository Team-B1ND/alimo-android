package com.b1nd.alimo.presentation.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.b1nd.alimo.data.Resource
import com.b1nd.alimo.data.repository.HomeRepository
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
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): BaseViewModel() {
    val pagingData = repository.getPost().cachedIn(viewModelScope)

    private val _sideEffect = Channel<HomeSideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val _categoryData: MutableStateFlow<List<HomeCategoryRvItem>> = MutableStateFlow(emptyList())
    val categoryData = _categoryData.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategory().collectLatest {
                when(it) {
                    is Resource.Error -> {
                        _sideEffect.send(HomeSideEffect.NotFound(HomeFound.Category))
                    }
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        if (it.data == null) {
                            _sideEffect.send(HomeSideEffect.NotFound(HomeFound.Category))
                            return@collectLatest
                        }
                        _categoryData.value = it.data.data.roles.map { category ->
                            HomeCategoryRvItem(category, false)
                        }
                    }
                }
            }
        }
    }
}