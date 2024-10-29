package com.alphazetakapp.misoappvinilos.di

import com.alphazetakapp.misoappvinilos.data.remote.service.AlbumService
import com.alphazetakapp.misoappvinilos.utils.NetworkConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//Here we are looking for a single instance to retrofit.
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String {
        return NetworkConstants.BASE_URL
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit { // Receives the URL of the previous method
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson to convert JSON
            .build()
    }

    @Provides
    @Singleton
    fun provideAlbumService(retrofit: Retrofit): AlbumService {
        return retrofit.create(AlbumService::class.java)
    }
}