package com.test.bountifarm.di

import com.test.bountifarm.data.DefaultMusicListPagingSourceFactory
import com.test.bountifarm.data.MusicListPagingSourceFactory
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface PagingModule {

    @Binds
    fun bindMusicListPagingSourceFactory(
        pagingSourceFactory: DefaultMusicListPagingSourceFactory
    ): MusicListPagingSourceFactory
}