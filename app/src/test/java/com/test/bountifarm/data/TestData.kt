package com.test.bountifarm.data

import com.test.bountifarm.data.model.SearchMusicListResponse
import com.test.bountifarm.domain.Music
import java.time.Duration
import java.util.concurrent.TimeUnit

object TestData {

    val musics = (1..5).map {
        Music(
            trackId = it.toLong(),
            artworkUrl = "artwork$it",
            trackName = "track$it",
            collectionName = "collection$it",
            releaseDate = "2021-01-01T12:00:00Z",
            artistName = "artist$it",
            trackTime = Duration.ofSeconds(it.toLong()),
        )
    }

    val musicData = (1..5).map {
        SearchMusicListResponse.Music(
            trackId = it.toLong(),
            artworkUrl100 = "artwork$it",
            trackName = "track$it",
            collectionName = "collection$it",
            releaseDate = "2021-01-01T12:00:00Z",
            artistName = "artist$it",
            trackTimeMillis = TimeUnit.SECONDS.toMillis(it.toLong()),
        )
    }
}