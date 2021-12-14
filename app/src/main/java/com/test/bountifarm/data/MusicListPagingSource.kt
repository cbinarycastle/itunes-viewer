package com.test.bountifarm.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.bountifarm.data.mapper.SearchMusicListResponseMapper
import com.test.bountifarm.data.model.SearchMusicListRequest
import com.test.bountifarm.data.model.SearchMusicListResponse
import com.test.bountifarm.domain.Music
import kotlinx.coroutines.delay
import timber.log.Timber
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toJavaDuration

class MusicListPagingSource(
    private val itunesService: ItunesService,
    private val responseMapper: SearchMusicListResponseMapper,
    private val term: String,
    private val entity: String = DEFAULT_ENTITY,
) : PagingSource<Int, Music>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Music> {
        return try {
            val request = SearchMusicListRequest(
                term = term,
                entity = entity,
                offset = params.key ?: INITIAL_KEY,
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

private const val INITIAL_KEY = 0
private const val DEFAULT_ENTITY = "song"