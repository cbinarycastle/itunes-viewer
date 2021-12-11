package com.test.bountifarm.data

import androidx.paging.PagingSource
import com.test.bountifarm.data.model.SearchMusicListResponse
import com.test.bountifarm.domain.Music
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toJavaDuration

class MusicListPagingSourceTest {

    @Test
    fun `음악 리스트를 성공적으로 로드한 경우, load()가 Page를 반환한다`() = runBlocking {
        // given
        val fakeItunesService = FakeItunesService().apply {
            TestData.musicData.forEach { addMusic(it) }
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
            data = listOf(TestData.musicData[0], TestData.musicData[1], TestData.musicData[2])
                .map { transformMusic(it) },
            prevKey = null,
            nextKey = 3
        )
        assertEquals(expected, actual)
    }

    private fun transformMusic(response: SearchMusicListResponse.Music): Music {
        return with(response) {
            Music(
                trackId = trackId,
                artworkUrl = artworkUrl100,
                trackName = trackName,
                collectionName = collectionName,
                releaseDate = releaseDate,
                artistName = artistName,
                trackTime = trackTimeMillis.milliseconds.toJavaDuration(),
            )
        }
    }
}