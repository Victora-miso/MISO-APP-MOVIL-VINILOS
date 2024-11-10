package com.alphazetakapp.misoappvinilos.data.remote.service

import com.alphazetakapp.misoappvinilos.data.remote.dto.CollectorDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CollectorService {
    @GET("collectors")
    suspend fun getCollectors(): Response<List<CollectorDTO>>

    @GET("collectors/{id}")
    suspend fun getCollectorsById(@Path("id") id: Int): Response<CollectorDTO>
}