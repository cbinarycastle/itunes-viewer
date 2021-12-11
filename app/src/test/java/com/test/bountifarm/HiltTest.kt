package com.test.bountifarm

import com.test.bountifarm.di.CoroutineModule
import com.test.bountifarm.di.DefaultDispatcher
import com.test.bountifarm.di.IoDispatcher
import com.test.bountifarm.di.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@UninstallModules(CoroutineModule::class)
@HiltAndroidTest
interface HiltTest {

    @ExperimentalCoroutinesApi
    @InstallIn(SingletonComponent::class)
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
}