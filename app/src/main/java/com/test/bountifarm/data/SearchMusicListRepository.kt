package com.test.bountifarm.data

import com.test.bountifarm.data.model.SearchMusicListRequest
import com.test.bountifarm.domain.Music
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchMusicListRepository @Inject constructor(
    private val itunesService: ItunesService,
) {

    suspend fun search(query: String): List<Music> {
        return itunesService
            .search(
                SearchMusicListRequest(query).toMap()
            )
            .results
            .map {
                with(it) {
                    Music(
                        artworkUrl = artworkUrl30,
                        trackName = trackName,
                        collectionName = collectionName,
                        releaseDate = releaseDate,
                        artistName = artistName,
                    )
                }
            }
    }
}