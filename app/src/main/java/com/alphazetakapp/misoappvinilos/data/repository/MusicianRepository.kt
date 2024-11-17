package com.alphazetakapp.misoappvinilos.data.repository

import com.alphazetakapp.misoappvinilos.data.local.dao.MusicianDao
import com.alphazetakapp.misoappvinilos.data.model.Musician
import com.alphazetakapp.misoappvinilos.data.remote.dto.toMusician
import com.alphazetakapp.misoappvinilos.data.remote.service.MusicianService
import javax.inject.Inject


class MusicianRepository @Inject constructor(private val musicianDao: MusicianDao,
                          private val musicianService: MusicianService
) {
    suspend fun getMusicians(): Result<List<Musician>> {
        return try {
            val response = musicianService.getMusicians()
            if (response.isSuccessful) {
                response.body()?.let { musiciansDTO ->
                    // Convert the DTO to Artist entities
                    val musicians = musiciansDTO.map { it.toMusician() }
                    // Save in cache
                    musicianDao.insertAll(musicians)
                    Result.success(musicians)
                } ?: Result.failure(Exception("Data is null"))
            } else {
                // If the API fails, we try recovery from cache
                Result.success(musicianDao.getAllMusicians())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMusicianById(id: String): Result<Musician> {
        return try {
            val response = musicianService.getMusicianById(id)
            if (response.isSuccessful) {
                response.body()?.let { musiciansDTO ->
                    Result.success(musiciansDTO.toMusician())
                } ?: Result.failure(Exception("Artist not found"))
            } else {
                val cachedAlbum = musicianDao.getMusicianById(id)
                cachedAlbum?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Artist not found in cache"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}