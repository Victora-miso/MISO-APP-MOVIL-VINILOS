package com.alphazetakapp.misoappvinilos.data.repository

import com.alphazetakapp.misoappvinilos.data.local.dao.AlbumDao
import com.alphazetakapp.misoappvinilos.data.model.Album
import com.alphazetakapp.misoappvinilos.data.model.CreateAlbum
import com.alphazetakapp.misoappvinilos.data.model.Track
import com.alphazetakapp.misoappvinilos.data.remote.dto.toAlbum
import com.alphazetakapp.misoappvinilos.data.remote.dto.toTrack
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
                // If the A  PI fails, we try recovery from cache
                Result.success(albumDao.getAllAlbums())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTracksByAlbumId(albumId: Int): Result<List<Track>> {
        return try {
            val response = albumService.getTracksByAlbumId(albumId)
            if (response.isSuccessful) {
                response.body()?.let { tracksDTO ->
                    val tracks = tracksDTO.map { it.toTrack() }
                    Result.success(tracks)
                    } ?: Result.failure(Exception("Data is null"))
            } else {
                Result.failure(Exception("Failed to fetch tracks"))
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
    suspend fun createAlbum(album: CreateAlbum): Result<Album> {
        return try {
            val response = albumService.createAlbum(album)
            if (response.isSuccessful) {
                response.body()?.let {albumDTO ->
                    Result.success(albumDTO.toAlbum())
                } ?: Result.failure(Exception("No album returned from server"))
            } else {
                Result.failure(Exception("Failed to create album: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun associateTrack(albumId:Int, track:Track): Result<Boolean> {
        return try {
            val response = albumService.associateTrack(albumId,track)
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Exception("Failed to associate track: ${response.errorBody()?.string()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}



