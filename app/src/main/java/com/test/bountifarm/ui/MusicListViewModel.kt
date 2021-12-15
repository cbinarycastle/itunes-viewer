package com.test.bountifarm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.bountifarm.domain.Result
import com.test.bountifarm.domain.SearchMusicListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MusicListViewModel @Inject constructor(
    searchMusicListUseCase: SearchMusicListUseCase,
) : ViewModel() {

    private val query = MutableStateFlow("")

    val musics = query
        .flatMapLatest {
            searchMusicListUseCase(it)
        }.map {
            when (it) {
                is Result.Success -> it.data
                is Result.Error -> PagingData.empty()
                Result.Loading -> PagingData.empty()
            }
        }.cachedIn(viewModelScope)

    private var previousLoadState: LoadState? = null

    private val loadState = MutableStateFlow<LoadState?>(null)

    private val _isResultEmpty = MutableStateFlow(false)
    val isResultEmpty = _isResultEmpty.asStateFlow()

    val isLoading = loadState
        .map { it == LoadState.Loading }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val isError = loadState
        .map { it is LoadState.Error }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

    val scrollTopEvent = loadState
        .filter { it is LoadState.NotLoading && it != previousLoadState }
        .map {}
        .shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    val errorMessage = loadState
        .filter { it is LoadState.Error }
        .map { (it as LoadState.Error).error.message }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    fun searchMusics(query: String) {
        this.query.value = query.trim()
    }

    fun onRefreshLoadStateChanged(loadState: LoadState, itemCount: Int) {
        previousLoadState = this.loadState.value
        this.loadState.value = loadState
        _isResultEmpty.value = loadState is LoadState.NotLoading && itemCount == 0
    }
}