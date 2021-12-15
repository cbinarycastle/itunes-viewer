package com.test.bountifarm

import com.test.bountifarm.data.SearchMusicListResponse
import com.test.bountifarm.domain.Music
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

object TestData {

    val musics = (1..5).map {
        Music(
            trackId = it.toLong(),
            artworkUrl = "artwork$it",
            trackName = "track$it",
            collectionName = "collection$it",
            releaseDate = LocalDateTime.of(2021, 1, it, 12, 0),
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
            releaseDate = "2021-01-0${it}T12:00:00Z",
            artistName = "artist$it",
            trackTimeMillis = TimeUnit.SECONDS.toMillis(it.toLong()),
        )
    }
}