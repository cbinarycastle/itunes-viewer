package com.test.bountifarm.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.bountifarm.domain.Result
import com.test.bountifarm.domain.SearchMusicListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MusicListViewModel @Inject constructor(
    searchMusicListUseCase: SearchMusicListUseCase,
) : ViewModel() {

    private val query = MutableStateFlow("")

    val musics = query.flatMapLatest {
        searchMusicListUseCase(it)
    }.map {
        when (it) {
            is Result.Success -> it.data
            is Result.Error -> PagingData.empty()
            Result.Loading -> PagingData.empty()
        }
    }.cachedIn(viewModelScope)

    fun searchMusics(query: String) {
        this.query.value = query.trim()
    }
}