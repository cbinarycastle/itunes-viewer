package com.test.bountifarm.data

import androidx.paging.PagingSource
import com.test.bountifarm.TestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class MusicListPagingSourceTest {

    @Test
    fun `음악 리스트를 성공적으로 로드한 경우, load()가 Page를 반환한다`() = runBlockingTest {
        // given
        val fakeItunesService = FakeItunesService().apply {
            TestData.musicData.forEach { addMusic(it) }
        }

        // when
        val actual = MusicListPagingSource(
            itunesService = fakeItunesService,
            responseMapper = SearchMusicListResponseMapper(),
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
                .mapIndexed { index, _ -> TestData.musics[index] },
            prevKey = null,
            nextKey = 3
        )
        assertEquals(expected, actual)
    }
}