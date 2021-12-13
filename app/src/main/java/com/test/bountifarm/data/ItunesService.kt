package com.test.bountifarm.data

import com.test.bountifarm.data.model.SearchMusicListResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ItunesService {

    @GET("/search")
    suspend fun search(@QueryMap params: Map<String, Any>): SearchMusicListResponse
}