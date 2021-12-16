package com.test.bountifarm.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.bountifarm.domain.Music
import timber.log.Timber

class MusicListPagingSource(
    private val itunesService: ItunesService,
    private val responseMapper: SearchMusicListResponseMapper,
    private val term: String,
    private val entity: String = DEFAULT_ENTITY,
    private val country: String = DEFAULT_COUNTRY,
) : PagingSource<Int, Music>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Music> {
        return try {
            val request = SearchMusicListRequest(
                term = term,
                entity = entity,
                country = country,
                offset = params.key ?: INITIAL_OFFSET,
                limit = params.loadSize,
            )

            val musics = itunesService
                .search(request.toMap())
                .results
                .map { responseMapper.toMusic(it) }

            LoadResult.Page(
                data = musics,
                prevKey = null,
                nextKey = if (musics.size == request.limit) {
                    request.offset + request.limit
                } else {
                    null
                }
            )
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Music>): Int? {
        return null
    }
}

private const val INITIAL_OFFSET = 0
private const val DEFAULT_ENTITY = "song"
private const val DEFAULT_COUNTRY = "KR"