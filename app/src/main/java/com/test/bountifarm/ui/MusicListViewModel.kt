package com.test.bountifarm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _errorMessage = MutableSharedFlow<Int>()
    val errorMessage = _errorMessage.asSharedFlow()

    val musics = query.flatMapLatest {
        searchMusicListUseCase(it)
    }.map {
        when (it) {
            is Result.Success -> it.data
            is Result.Error -> PagingData.empty()
            Result.Loading -> PagingData.empty()
        }
    }.cachedIn(viewModelScope)

    private val _initialLoading = MutableStateFlow(false)
    val initialLoading = _initialLoading.asStateFlow()

    fun searchMusics(query: String) {
        this.query.value = query
    }

    fun setInitialLoading(loading: Boolean) {
        _initialLoading.value = loading
    }
}