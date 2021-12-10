package com.test.bountifarm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
class CoroutineModule {

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher() = Dispatchers.Default

    @MainDispatcher
    @Provides
    fun provideMainDispatcher() = Dispatchers.Main

    @IoDispatcher
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}