package com.test.bountifarm.data.model

data class SearchMusicListResponse(
    val results: List<Music>,
) {
    data class Music(
        val artworkUrl30: String,
        val trackName: String,
        val collectionName: String,
        val releaseDate: String,
        val artistName: String,
    )
}