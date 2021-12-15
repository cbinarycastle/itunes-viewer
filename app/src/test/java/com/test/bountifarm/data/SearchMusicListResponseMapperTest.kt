package com.test.bountifarm.data

import com.test.bountifarm.TestData
import junit.framework.Assert.assertEquals
import org.junit.Test

class SearchMusicListResponseMapperTest {

    @Test
    fun `음악 리스트 검색 응답을 올바르게 변환한다`() {
        // given
        val mapper = SearchMusicListResponseMapper()

        // when
        val actual = mapper.toMusic(TestData.musicData[0])

        // then
        assertEquals(TestData.musics[0], actual)
    }
}