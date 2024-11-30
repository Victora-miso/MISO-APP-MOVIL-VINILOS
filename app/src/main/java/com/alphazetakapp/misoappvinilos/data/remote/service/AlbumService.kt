package com.alphazetakapp.misoappvinilos.data.remote.service

import com.alphazetakapp.misoappvinilos.data.remote.dto.AlbumDTO
import com.alphazetakapp.misoappvinilos.data.model.CreateAlbum
import com.alphazetakapp.misoappvinilos.data.model.Track
import com.alphazetakapp.misoappvinilos.data.remote.dto.TrackDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body

interface AlbumService {
    @GET("albums")
    suspend fun getAlbums(): Response<List<AlbumDTO>>

    @GET("albums/{id}")
    suspend fun getAlbumById(@Path("id") id: Int): Response<AlbumDTO>

    @POST("albums")
    suspend fun createAlbum(@Body album: CreateAlbum): Response<AlbumDTO>

    @POST("albums/{new_id_a}/tracks")
    suspend fun associateTrack(
        @Path("new_id_a") albumId: Int,
        @Body track: Track
    ): Response<TrackDTO>
}