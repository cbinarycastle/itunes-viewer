package com.test.bountifarm.di

import com.test.bountifarm.data.ItunesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun provideItunesService(): ItunesService {
        val client = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

        return Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItunesService::class.java)
    }
}