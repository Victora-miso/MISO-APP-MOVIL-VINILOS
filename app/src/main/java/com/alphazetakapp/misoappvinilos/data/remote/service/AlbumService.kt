package com.alphazetakapp.misoappvinilos.data.remote.service

import com.alphazetakapp.misoappvinilos.data.remote.dto.AlbumDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumService {
    @GET("albums")
    suspend fun getAlbums(): Response<List<AlbumDTO>>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: Int): Response<AlbumDTO>
}