package com.alphazetakapp.misoappvinilos.data.repository

import com.alphazetakapp.misoappvinilos.data.local.dao.AlbumDao
import com.alphazetakapp.misoappvinilos.data.model.Album
import com.alphazetakapp.misoappvinilos.data.remote.dto.toAlbum
import com.alphazetakapp.misoappvinilos.data.remote.service.AlbumService
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val albumDao: AlbumDao,
    private val albumService: AlbumService
) {

    suspend fun getAlbums(): Result<List<Album>> {
        return try {
            val response = albumService.getAlbums()
            if (response.isSuccessful) {
                response.body()?.let { albumsDTO ->
                    // Convert the DTO to Album entities
                    val albums = albumsDTO.map { it.toAlbum() }
                    // Save in cache
                    albumDao.insertAll(albums)
                    Result.success(albums)
                } ?: Result.failure(Exception("Data is null"))
            } else {
                // If the API fails, we try recovery from cache
                Result.success(albumDao.getAllAlbums())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAlbumById(id: Int): Result<Album> {
        return try {
            val response = albumService.getAlbumById(id)
            if (response.isSuccessful) {
                response.body()?.let { albumDTO ->
                    Result.success(albumDTO.toAlbum())
                } ?: Result.failure(Exception("Album not found"))
            } else {
                val cachedAlbum = albumDao.getAlbumById(id)
                cachedAlbum?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Album not found in cache"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}



