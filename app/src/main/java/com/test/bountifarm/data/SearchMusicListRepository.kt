package com.test.bountifarm.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.test.bountifarm.domain.Music
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchMusicListRepository @Inject constructor(
    private val musicListPagingSourceFactory: MusicListPagingSourceFactory,
) {
    fun getSearchResultStream(query: String): Flow<PagingData<Music>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { musicListPagingSourceFactory.create(query) },
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}