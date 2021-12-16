package com.test.bountifarm.data

data class SearchMusicListRequest(
    val term: String,
    val entity: String,
    val country: String,
    val offset: Int,
    val limit: Int,
) {
    fun toMap() = mapOf<String, Any>(
        TERM to term,
        ENTITY to entity,
        COUNTRY to country,
        OFFSET to offset,
        LIMIT to limit,
    )

    companion object {
        const val TERM = "term"
        const val ENTITY = "entity"
        const val COUNTRY = "country"
        const val OFFSET = "offset"
        const val LIMIT = "limit"
    }
}