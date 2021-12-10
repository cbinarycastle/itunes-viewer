package com.test.bountifarm.data

import com.test.bountifarm.domain.Music

object TestData {

    val musics = (1..5).map {
        Music(
            artworkUrl = "artwork$it",
            trackName = "track$it",
            collectionName = "collection$it",
            releaseDate = "2021-01-01T12:00:00Z",
            artistName = "artist$it",
        )
    }
}