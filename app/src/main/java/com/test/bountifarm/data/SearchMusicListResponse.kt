package com.test.bountifarm.data

data class SearchMusicListResponse(
    val results: List<Music>,
) {
    data class Music(
        val trackId: Long,
        val artworkUrl100: String,
        val trackName: String,
        val collectionName: String,
        val releaseDate: String,
        val artistName: String,
        val trackTimeMillis: Long,
    )
}