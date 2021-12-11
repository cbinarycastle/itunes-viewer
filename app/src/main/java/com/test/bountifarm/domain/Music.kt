package com.test.bountifarm.domain

import java.time.Duration
import java.time.LocalDateTime

data class Music(
    val trackId: Long,
    val artworkUrl: String,
    val trackName: String,
    val collectionName: String,
    val releaseDate: LocalDateTime,
    val artistName: String,
    val trackTime: Duration,
)