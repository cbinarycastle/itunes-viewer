package com.test.bountifarm.data

import com.test.bountifarm.data.model.SearchMusicListRequest
import com.test.bountifarm.data.model.SearchMusicListResponse

class FakeItunesService : ItunesService {

    private val musics = mutableListOf<SearchMusicListResponse.Music>()

    override suspend fun search(params: Map<String, Any>): SearchMusicListResponse {
        val offset = params[SearchMusicListRequest.OFFSET] as Int
        val limit = params[SearchMusicListRequest.LIMIT] as Int

        val fromIndex = if (offset <= musics.lastIndex) {
            offset
        } else {
            return SearchMusicListResponse(emptyList())
        }
        val toIndex = if (offset + limit <= musics.size) {
            offset + limit
        } else {
            musics.size
        }

        return SearchMusicListResponse(musics.subList(fromIndex, toIndex))
    }

    fun addMusic(music: SearchMusicListResponse.Music) {
        musics.add(music)
    }
}