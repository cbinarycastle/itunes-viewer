package com.test.bountifarm.ui

import androidx.lifecycle.ViewModel
import com.test.bountifarm.domain.SearchMusicListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MusicListViewModel @Inject constructor(
    searchMusicListUseCase: SearchMusicListUseCase,
) : ViewModel() {

    private val query = MutableStateFlow("")

    val musics = query.flatMapLatest {
        searchMusicListUseCase(it)
    }

    fun searchMusics(query: String) {
        this.query.value = query
    }
}