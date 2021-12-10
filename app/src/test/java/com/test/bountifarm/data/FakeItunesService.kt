package com.test.bountifarm.data

import com.test.bountifarm.data.model.SearchMusicListRequest
import com.test.bountifarm.data.model.SearchMusicListResponse

class FakeItunesService : ItunesService {

    private val musics = mutableListOf<SearchMusicListResponse.Music>()

    override suspend fun search(params: Map<String, Any>): SearchMusicListResponse {
        val limit = params[SearchMusicListRequest.LIMIT] as Int
        return SearchMusicListResponse(musics.take(limit))
    }

    fun addMusic(music: SearchMusicListResponse.Music) {
        musics.add(music)
    }
}