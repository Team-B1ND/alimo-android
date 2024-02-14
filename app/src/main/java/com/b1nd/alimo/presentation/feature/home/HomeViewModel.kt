package com.b1nd.alimo.presentation.feature.home

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.b1nd.alimo.data.repository.HomeRepository
import com.b1nd.alimo.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): BaseViewModel() {
    val pagingData = repository.getNotice().cachedIn(viewModelScope)
}