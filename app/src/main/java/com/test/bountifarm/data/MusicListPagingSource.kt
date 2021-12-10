package com.test.bountifarm.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.bountifarm.data.model.SearchMusicListRequest
import com.test.bountifarm.domain.Music

class MusicListPagingSource(
    private val itunesService: ItunesService,
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
                .map {
                    with(it) {
                        Music(
                            artworkUrl = artworkUrl30,
                            trackName = trackName,
                            collectionName = collectionName,
                            releaseDate = releaseDate,
                            artistName = artistName,
                        )
                    }
                }

            LoadResult.Page(
                data = musics,
                prevKey = null,
                nextKey = request.offset + request.limit,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Music>): Int? {
        return null
    }

    companion object {
        private const val INITIAL_KEY = 0
        private const val DEFAULT_ENTITY = "song"
    }
}