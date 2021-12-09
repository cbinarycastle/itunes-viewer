package com.test.bountifarm.data.model

data class SearchMusicListRequest(
    val term: String,
) {
    fun toMap() = mapOf<String, Any>(
        "term" to term,
        "entity" to "song",
    )
}