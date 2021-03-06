package com.test.bountifarm.data

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ItunesService {

    @JvmSuppressWildcards
    @GET("/search")
    suspend fun search(@QueryMap params: Map<String, Any>): SearchMusicListResponse
}