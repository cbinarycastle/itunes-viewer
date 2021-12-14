package com.test.bountifarm.data

import androidx.paging.PagingSource
import com.test.bountifarm.data.mapper.SearchMusicListResponseMapper
import com.test.bountifarm.domain.Music
import javax.inject.Inject
import javax.inject.Singleton

interface MusicListPagingSourceFactory {
    fun create(term: String): PagingSource<Int, Music>
}

@Singleton
class DefaultMusicListPagingSourceFactory @Inject constructor(
    private val itunesService: ItunesService,
    private val responseMapper: SearchMusicListResponseMapper,
) : MusicListPagingSourceFactory {

    override fun create(term: String): PagingSource<Int, Music> {
        return MusicListPagingSource(
            itunesService = itunesService,
            responseMapper = responseMapper,
            term = term
        )
    }
}