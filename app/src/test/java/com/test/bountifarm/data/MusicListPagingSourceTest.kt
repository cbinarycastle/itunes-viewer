package com.test.bountifarm.data

import androidx.paging.PagingSource
import com.test.bountifarm.data.model.SearchMusicListResponse
import com.test.bountifarm.domain.Music
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class MusicListPagingSourceTest {

    private val mockMusicResponses = (1..5).map { createMockMusicResponse(it) }

    @Test
    fun `음악 리스트를 성공적으로 로드한 경우, load()가 Page를 반환한다`() = runBlocking {
        // given
        val fakeItunesService = FakeItunesService().apply {
            mockMusicResponses.forEach { addMusic(it) }
        }

        // when
        val actual = MusicListPagingSource(
            itunesService = fakeItunesService,
            term = "test",
        )
            .load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 3,
                    placeholdersEnabled = false,
                )
            )

        // then
        val expected = PagingSource.LoadResult.Page(
            data = listOf(mockMusicResponses[0], mockMusicResponses[1], mockMusicResponses[2])
                .map { transformMusic(it) },
            prevKey = null,
            nextKey = 3
        )
        assertEquals(expected, actual)
    }

    private fun createMockMusicResponse(index: Int): SearchMusicListResponse.Music {
        return SearchMusicListResponse.Music(
            artworkUrl30 = "artwork$index",
            trackName = "track$index",
            collectionName = "collection$index",
            releaseDate = "2021-01-01T12:00:00Z",
            artistName = "artist$index",
        )
    }

    private fun transformMusic(response: SearchMusicListResponse.Music): Music {
        return with(response) {
            Music(
                artworkUrl = artworkUrl30,
                trackName = trackName,
                collectionName = collectionName,
                releaseDate = releaseDate,
                artistName = artistName,
            )
        }
    }
}