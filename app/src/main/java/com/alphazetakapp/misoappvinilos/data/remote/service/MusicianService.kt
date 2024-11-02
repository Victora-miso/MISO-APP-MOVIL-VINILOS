package com.alphazetakapp.misoappvinilos.data.remote.service

import com.alphazetakapp.misoappvinilos.data.remote.dto.MusicianDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicianService {
    @GET("musicians")
    suspend fun getMusicians(): Response<List<MusicianDTO>>

    @GET("musicians/{id}")
    suspend fun getMusicianById(@Path("id") id: String): Response<MusicianDTO>
}