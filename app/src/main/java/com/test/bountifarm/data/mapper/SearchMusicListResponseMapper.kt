package com.test.bountifarm.data.mapper

import com.test.bountifarm.data.model.SearchMusicListResponse
import com.test.bountifarm.domain.Music
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.toJavaDuration

@Singleton
class SearchMusicListResponseMapper @Inject constructor() {

    fun toMusic(from: SearchMusicListResponse.Music): Music {
        return from.run {
            Music(
                trackId = trackId,
                artworkUrl = artworkUrl100,
                trackName = trackName,
                collectionName = collectionName,
                releaseDate = ZonedDateTime
                    .parse(releaseDate)
                    .toLocalDateTime(),
                artistName = artistName,
                trackTime = trackTimeMillis.milliseconds.toJavaDuration(),
            )
        }
    }
}