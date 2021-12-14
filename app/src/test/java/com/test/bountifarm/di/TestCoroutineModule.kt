package com.test.bountifarm.di

import com.test.bountifarm.di.CoroutineModule
import com.test.bountifarm.di.DefaultDispatcher
import com.test.bountifarm.di.IoDispatcher
import com.test.bountifarm.di.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoroutineModule::class]
)
@Module
class TestCoroutineModule {

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = TestCoroutineDispatcher()

    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = TestCoroutineDispatcher()

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = TestCoroutineDispatcher()
}