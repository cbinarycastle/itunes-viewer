package com.test.bountifarm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
class CoroutineModule {

    @Provides
    fun provideDefaultDispatcher() = Dispatchers.Default

    @Provides
    fun provideMainDispatcher() = Dispatchers.Main

    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}